import React, { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import { Row, Col, Card, Form, Input, Button, Modal, Divider, Tree } from 'antd';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import { validateCodeUnique } from '@/services/sys/role';
import { formatParams } from '@/utils/utils';
import Authorized from '@/utils/Authorized';
import styles from './Role.less';

const FormItem = Form.Item;
const TreeNode = Tree.TreeNode;

@connect(({ role, right, loading }) => ({
  role,
  allRights: right.allRights,
  roleRights: right.roleRights,
  loading: loading.models.role,
}))
@Form.create()
class RolePage extends PureComponent {
  state = {
    selectedRows: [], //**选中的行
    formValues: {}, //**搜索数据
    modalVisible: false, //**显示编译页面
    editFormValues: {}, //**编辑数据

    allotRightModalVisible: false, //**显示分配权限页面
    id: null, //**分配权限的角色id
    checkRoleRights: [], //**选中角色的权限
  };

  /**table columns */
  columns = [
    { title: '编号', dataIndex: 'id', key: 'id' },
    { title: '编码', dataIndex: 'code' },
    { title: '名称', dataIndex: 'name' },
    { title: '描述', dataIndex: 'description' },
    {
      title: '操作',
      render: (text, record) => (
        <Fragment>
          <Authorized authority="sys:role:save">
            <a onClick={() => this.handleModalVisible(true, record)}>修改</a>
          </Authorized>
          {record.code !== 'admin' && (
            <Authorized authority="sys:role:allotRight">
              <Divider type="vertical" />
              <a onClick={() => this.handleAllotRightModalVisible(true, record.id)}>分配权限</a>
            </Authorized>
          )}
          <Authorized authority="sys:role:delete">
            <Divider type="vertical" />
            <a onClick={() => this.handleDelete(record.id)}>删除</a>
          </Authorized>
        </Fragment>
      ),
    },
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({ type: 'role/fetch' });
    dispatch({ type: 'right/fetchAll' });
  }

  /**搜索 */
  handleSearch = e => {
    e.preventDefault();

    const { dispatch, form } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;

      this.setState({ formValues: fieldsValue });
      dispatch({ type: 'role/fetch', payload: fieldsValue });
    });
  };

  /**重置 */
  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState({ formValues: {} });

    dispatch({ type: 'role/fetch' });
  };

  //**复选框 */
  handleSelectRows = rows => {
    this.setState({ selectedRows: rows });
  };

  /**改变事件*/
  handleStandardTableChange = pagination => {
    const { dispatch } = this.props;
    const { formValues } = this.state;

    const params = { ...pagination, ...formValues };
    dispatch({ type: 'user/fetch', payload: params });
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
      type: 'role/saveObj',
      payload: formatParams(fields),
      callback: () => {
        this.handleModalVisible();
        dispatch({ type: 'role/fetch', payload: formValues });
      },
    });
  };

  //**显示分配权限页面*/
  handleAllotRightModalVisible = (flag, id) => {
    const show = !!flag;
    const { dispatch } = this.props;

    if (show) {
      dispatch({ type: 'right/fetchByRoleId', payload: id }).then(() => {
        const { roleRights } = this.props;
        const roleRightIds = roleRights.map(right => right.value);
        this.setState({ checkRoleRights: roleRightIds });
      });
    }

    this.setState({ allotRightModalVisible: show, id: id || null });
  };

  //**分配权限*/
  handleAllotRight = e => {
    e.preventDefault();
    const { dispatch } = this.props;
    const { id, checkRoleRights } = this.state;

    if (!checkRoleRights.length) return;
    dispatch({ type: 'role/allotRight', payload: { id, rights: checkRoleRights } }).then(() =>
      this.handleAllotRightModalVisible()
    );
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
          type: 'role/deleteById',
          payload: id,
          callback: () => dispatch({ type: 'role/fetch', payload: formValues }),
        }),
    });
  };

  //**渲染树节点
  renderTreeNodes = data =>
    data.map(item => {
      if (item.children) {
        return (
          <TreeNode title={item.title} key={item.value}>
            {this.renderTreeNodes(item.children)}
          </TreeNode>
        );
      }
      return <TreeNode title={item.title} key={item.value} />;
    });

  //**选中分配权限的复选框
  handleCheckRoleRight = checkedKeys => {
    this.setState({ checkRoleRights: checkedKeys });
  };

  //**搜索表单*/
  renderSearchForm() {
    const {
      form: { getFieldDecorator },
    } = this.props;

    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={12} sm={24}>
            <FormItem label="编码">
              {getFieldDecorator('code')(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={12} sm={24}>
            <FormItem label="名称">
              {getFieldDecorator('name')(<Input placeholder="请输入" />)}
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
      role: { data },
      loading,
      allRights,
    } = this.props;
    const {
      selectedRows,
      modalVisible,
      editFormValues,
      allotRightModalVisible,
      checkRoleRights,
    } = this.state;

    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>{this.renderSearchForm()}</div>
            <div className={styles.tableListOperator}>
              <Authorized authority="sys:role:save">
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
        />
        <Modal
          destroyOnClose
          title="分配权限"
          visible={allotRightModalVisible}
          onCancel={() => this.handleAllotRightModalVisible()}
          onOk={this.handleAllotRight}
        >
          <Row gutter={24}>
            <Col span={24}>
              <Tree
                checkable
                showLine
                checkedKeys={checkRoleRights}
                onCheck={this.handleCheckRoleRight}
              >
                {this.renderTreeNodes(allRights)}
              </Tree>
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

      const params = values && values.id ? { id: values.id, ...fieldsValue } : fieldsValue;
      handleEdit(params);
    });
  };

  render() {
    const { form, modalVisible, handleModalVisible, values } = this.props;
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
            <FormItem label="名称" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('name', {
                rules: [{ required: true, max: 10, message: '请输入角色名称(最大10个字符)' }],
                initialValue: values.name || '',
              })(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="编码" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {operation === 'add' &&
                form.getFieldDecorator('code', {
                  rules: [
                    { required: true, min: 4, max: 10, message: '请输入角色编码(4-10个字符)' },
                    {
                      validator: (rule, val, callback) => {
                        if (!val || val.length < 4) callback();
                        else {
                          validateCodeUnique(val).then(response => {
                            if (response.data === false) callback('角色编码已经存在');

                            callback();
                          });
                        }
                      },
                    },
                  ],
                  validateTrigger: 'onBlur',
                  initialValue: values.code || '',
                })(<Input placeholder="请输入" />)}
              {operation === 'update' && values.code}
            </FormItem>
          </Col>
        </Row>
        <Row gutter={24}>
          <Col span={12}>
            <FormItem label="描述" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('description', {
                rules: [{ max: 50, message: '请输入角色描述(最大50个字符)' }],
                initialValue: values.description,
              })(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
        </Row>
      </Modal>
    );
  }
}

export default RolePage;
