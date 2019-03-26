import React, { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import {
  Row,
  Col,
  Card,
  Form,
  Input,
  Select,
  Button,
  DatePicker,
  Modal,
  Divider,
  Radio,
  TreeSelect,
} from 'antd';
import moment from 'moment';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import { validateUserNameUnique } from '@/services/sys/user';
import { formatParams } from '@/utils/utils';
import Authorized from '@/utils/Authorized';
import styles from './User.less';

const FormItem = Form.Item;
const Option = Select.Option;
const RadioGroup = Radio.Group;

@connect(({ user, dept, role, loading }) => ({
  user,
  allDepts: dept.allDepts,
  allRoles: role.allRoles,
  userRoles: role.userRoles,
  loading: loading.models.user,
}))
@Form.create()
class UserPage extends PureComponent {
  state = {
    selectedRows: [], //**选中的行
    formValues: {}, //**搜索数据
    modalVisible: false, //**显示编辑页面
    editFormValues: {}, //**编辑数据

    allotRoleModalVisible: false, //**显示分配角色页面
    id: null, //**分配角色的用户id
  };

  /**table columns */
  columns = [
    { title: '编号', dataIndex: 'id', key: 'id' },
    { title: '姓名', dataIndex: 'name' },
    { title: '用户名称', dataIndex: 'userName' },
    { title: '性别', dataIndex: 'sex', render: val => (val === 1 ? '女' : '男') },
    { title: '年龄', dataIndex: 'age' },
    { title: '部门', dataIndex: 'deptName' },
    { title: '手机号', dataIndex: 'mobile' },
    {
      title: '生日',
      dataIndex: 'birthday',
      render: val => <span>{val ? moment(val).format('YYYY-MM-DD') : ''}</span>,
    },
    { title: '启用', dataIndex: 'enabled', render: val => (val === 1 ? '启用' : '禁用') },
    {
      title: '操作',
      render: (text, record) => (
        <Fragment>
          <Authorized authority="sys:user:save">
            <a onClick={() => this.handleModalVisible(true, record)}>修改</a>
          </Authorized>
          <Authorized authority="sys:user:allotRole">
            <Divider type="vertical" />
            <a onClick={() => this.handleAllotRoleModalVisible(true, record.id)}>分配角色</a>
          </Authorized>
          <Authorized authority="sys:user:delete">
            <Divider type="vertical" />
            <a onClick={() => this.handleDelete(record.id)}>删除</a>
          </Authorized>
        </Fragment>
      ),
    },
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({ type: 'user/fetch' });
    dispatch({ type: 'dept/fetchAll' });
    dispatch({ type: 'role/fetchAll' });
  }

  /**搜索 */
  handleSearch = e => {
    e.preventDefault();

    const { dispatch, form } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;

      this.setState({ formValues: fieldsValue });
      dispatch({ type: 'user/fetch', payload: fieldsValue });
    });
  };

  /**改变事件*/
  handleStandardTableChange = pagination => {
    const { dispatch } = this.props;
    const { formValues } = this.state;

    const params = { ...pagination, ...formValues };
    dispatch({ type: 'user/fetch', payload: params });
  };

  /**重置 */
  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState({ formValues: {} });

    dispatch({ type: 'user/fetch' });
  };

  //**复选框 */
  handleSelectRows = rows => {
    this.setState({ selectedRows: rows });
  };

  //**显示编辑页面*/
  handleModalVisible = (flag, record) => {
    this.setState({ modalVisible: !!flag, editFormValues: record || {} });
  };

  //**编辑提交到后台 */
  handleEdit = fields => {
    const { dispatch } = this.props;
    const { formValues } = this.state;

    dispatch({
      type: 'user/saveObj',
      payload: formatParams(fields),
      callback: () => {
        this.handleModalVisible();
        dispatch({ type: 'user/fetch', payload: formValues });
      },
    });
  };

  //**显示分配角色页面*/
  handleAllotRoleModalVisible = (flag, id) => {
    const show = !!flag;
    const { dispatch } = this.props;

    this.setState({ allotRoleModalVisible: show, id: id || null });
    if (show) {
      dispatch({ type: 'role/fetchByUserId', payload: id });
    }
  };

  //**分配角色*/
  handleAllotRole = e => {
    e.preventDefault();
    const { form, dispatch } = this.props;
    const { id } = this.state;

    const roles = form.getFieldValue('roles');
    if (!roles.length) return;

    dispatch({
      type: 'user/allotRole',
      payload: { id, roles },
      callback: () => {
        this.handleAllotRoleModalVisible();
      },
    });
  };

  //**删除 */
  handleDelete = id => {
    const { dispatch } = this.props;
    const { formValues } = this.state;

    Modal.confirm({
      title: '删除',
      content: '确定删除吗？',
      okText: '确认',
      cancelText: '取消',
      onOk: () =>
        dispatch({
          type: 'user/deleteById',
          payload: id,
          callback: () => dispatch({ type: 'user/fetch', payload: formValues }),
        }),
    });
  };

  //**搜索表单*/
  renderSearchForm() {
    const {
      form: { getFieldDecorator },
      allDepts,
    } = this.props;

    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="姓名">
              {getFieldDecorator('name')(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="用户名称">
              {getFieldDecorator('userName')(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="部门">
              {getFieldDecorator('deptId')(
                <TreeSelect
                  dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                  treeData={allDepts}
                  placeholder="请选择"
                />
              )}
            </FormItem>
          </Col>
        </Row>
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="启用">
              {getFieldDecorator('enabled')(
                <Select style={{ width: '100%' }} placeholder="请选择">
                  <Option value="1">启用</Option>
                  <Option value="0">禁用</Option>
                </Select>
              )}
            </FormItem>
          </Col>
        </Row>

        <div style={{ overflow: 'hidden' }}>
          <div style={{ float: 'right', marginBottom: 24 }}>
            <Button type="primary" htmlType="submit">
              {' '}
              查询{' '}
            </Button>
            <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>
              {' '}
              重置{' '}
            </Button>
          </div>
        </div>
      </Form>
    );
  }

  render() {
    const {
      user: { data },
      loading,
      form,
      allDepts,
      allRoles,
      userRoles,
    } = this.props;
    const { selectedRows, modalVisible, editFormValues, allotRoleModalVisible } = this.state;
    const userRoleIds = userRoles.map(role => role.id);

    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>{this.renderSearchForm()}</div>
            <div className={styles.tableListOperator}>
              <Authorized authority="sys:user:save">
                <Button icon="plus" type="primary" onClick={() => this.handleModalVisible(true)}>
                  新建
                </Button>
              </Authorized>
            </div>
            <StandardTable
              rowKey="id"
              selectedRows={selectedRows}
              loading={loading}
              data={data}
              columns={this.columns}
              onSelectRow={this.handleSelectRows}
              onChange={this.handleStandardTableChange}
            />
          </div>
        </Card>
        <EditForm
          modalVisible={modalVisible}
          handleModalVisible={this.handleModalVisible}
          handleEdit={this.handleEdit}
          values={editFormValues}
          allDepts={allDepts}
        />
        <Modal
          destroyOnClose
          title="分配角色"
          visible={allotRoleModalVisible}
          onCancel={() => this.handleAllotRoleModalVisible()}
          onOk={this.handleAllotRole}
        >
          <Row gutter={24}>
            <Col span={24}>
              <FormItem wrapperCol={{ span: 24 }}>
                {form.getFieldDecorator('roles', {
                  rules: [{ required: true, message: '请选择角色' }],
                  initialValue: userRoleIds,
                })(
                  <Select mode="multiple" style={{ width: 230 }} placeholder="请选择">
                    {allRoles.map(role => (
                      <Option key={role.id} value={role.id}>
                        {role.code}
                      </Option>
                    ))}
                  </Select>
                )}
              </FormItem>
            </Col>
          </Row>
        </Modal>
      </PageHeaderWrapper>
    );
  }
}

/**编辑表单 */
@Form.create()
class EditForm extends PureComponent {
  /**确定按钮 */
  okHandle = e => {
    e.preventDefault();
    const { handleEdit, form, values } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;

      const params =
        values && values.id
          ? { id: values.id, userName: values.userName, ...fieldsValue }
          : fieldsValue;
      handleEdit(params);
    });
  };

  render() {
    const { form, modalVisible, handleModalVisible, values, allDepts } = this.props;
    const birthday = values.birthday ? moment(values.birthday) : null;
    const operation = values && Object.keys(values).length ? 'update' : 'add';

    return (
      <Modal
        destroyOnClose
        title={operation === 'update' ? '编辑' : '添加'}
        visible={modalVisible}
        onCancel={() => handleModalVisible()}
        onOk={this.okHandle}
      >
        <Row gutter={24}>
          <Col span={12}>
            <FormItem label="姓名" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('name', {
                rules: [{ required: true, max: 10, message: '请输入姓名(最大10个字符)' }],
                initialValue: values.name || '',
              })(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="用户名称" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {operation === 'add' &&
                form.getFieldDecorator('userName', {
                  rules: [
                    { required: true, min: 4, max: 10, message: '请输入用户名称(4-10个字符)' },
                    {
                      validator: (rule, val, callback) => {
                        if (!val || val.length < 4) callback();
                        else {
                          validateUserNameUnique(val).then(response => {
                            if (response.data === false) callback('用户名称已经存在');

                            callback();
                          });
                        }
                      },
                    },
                  ],
                  validateTrigger: 'onBlur',
                  initialValue: values.userName || '',
                })(<Input placeholder="请输入" />)}
              {operation === 'update' && values.userName}
            </FormItem>
          </Col>
        </Row>
        <Row gutter={24}>
          <Col span={12}>
            <FormItem label="部门" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('deptId', {
                rules: [{ required: true, message: '请选择部门' }],
                initialValue: values.deptId,
              })(
                <TreeSelect
                  style={{ width: 150 }}
                  width={80}
                  treeData={allDepts}
                  placeholder="请选择"
                />
              )}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="手机号" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('mobile', {
                rules: [{ len: 11, message: '请输入手机号(11个字符)' }],
                initialValue: values.mobile,
              })(<Input type="number" placeholder="请输入" />)}
            </FormItem>
          </Col>
        </Row>
        {operation === 'add' && (
          <Row gutter={24}>
            <Col span={12}>
              <FormItem label="密码" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
                {form.getFieldDecorator('password', {
                  rules: [{ required: true, min: 4, max: 10, message: '请输入密码(4-10个字符)' }],
                })(<Input type="password" placeholder="请输入" />)}
              </FormItem>
            </Col>
            <Col span={12}>
              <FormItem label="确认密码" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
                {form.getFieldDecorator('rePassword', {
                  rules: [
                    { required: true, message: '请输入确认密码' },
                    {
                      validator: (rule, val, callback) => {
                        if (!val) callback();
                        else if (form.getFieldValue('password') !== val)
                          callback('确认密码和密码不一致');
                        callback();
                      },
                    },
                  ],
                })(<Input type="password" placeholder="请输入" />)}
              </FormItem>
            </Col>
          </Row>
        )}
        <Row gutter={24}>
          <Col span={12}>
            <FormItem label="性别" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('sex', { initialValue: values.sex === 1 ? 1 : 0 })(
                <RadioGroup>
                  <Radio value={0}>男</Radio>
                  <Radio value={1}>女</Radio>
                </RadioGroup>
              )}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="年龄" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('age', {
                rules: [
                  {
                    type: 'integer',
                    max: 200,
                    transform: value => (value ? Number.parseInt(value, 10) : value),
                    message: '请输入年龄(0-200)',
                  },
                ],
                initialValue: values.age,
              })(<Input type="number" placeholder="请输入" />)}
            </FormItem>
          </Col>
        </Row>
        <Row gutter={24}>
          <Col span={12}>
            <FormItem label="生日" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('birthday', { initialValue: birthday })(
                <DatePicker format="YYYY-MM-DD" />
              )}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="是否启用" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('enabled', { initialValue: values.enabled === 0 ? 0 : 1 })(
                <RadioGroup>
                  <Radio value={1}>启用</Radio>
                  <Radio value={0}>禁用</Radio>
                </RadioGroup>
              )}
            </FormItem>
          </Col>
        </Row>
      </Modal>
    );
  }
}

export default UserPage;
