import React, { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import { Row, Col, Card, Form, Input, Button, Modal, Divider } from 'antd';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import { validateCodeUnique } from '@/services/sys/role';
import { formatParams } from '@/utils/utils';
import Authorized from '@/utils/Authorized';
import styles from './Role.less';

const FormItem = Form.Item;

@connect(({ role, loading }) => ({
  role,
  loading: loading.models.role,
}))
@Form.create()
class RolePage extends PureComponent {
  state = {
    selectedRows: [], //**选中的行
    formValues: {}, //**搜索数据
    modalVisible: false, //**显示编译页面
    editFormValues: {}, //**编辑数据
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
          <Divider type="vertical" />
          <Authorized authority="sys:role:delete">
            <a onClick={() => this.handleDelete(record.id)}>删除</a>
          </Authorized>
        </Fragment>
      ),
    },
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({ type: 'role/fetch' });
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
    } = this.props;
    const { selectedRows, modalVisible, editFormValues } = this.state;

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
