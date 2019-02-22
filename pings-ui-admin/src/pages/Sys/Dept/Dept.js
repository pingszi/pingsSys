import React, { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import { Row, Col, Card, Form, Input, Button, Modal, Divider, TreeSelect } from 'antd';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import { validateCodeUnique } from '@/services/sys/dept';
import { formatParams } from '@/utils/utils';
import Authorized from '@/utils/Authorized';
import styles from './Dept.less';

const FormItem = Form.Item;

@connect(({ dept, loading }) => ({
  dept,
  loading: loading.models.user,
}))
@Form.create()
class DeptPage extends PureComponent {
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
          <Authorized authority="sys:dept:save">
            <a onClick={() => this.handleModalVisible(true, record)}>修改</a>
          </Authorized>
          <Divider type="vertical" />
          <Authorized authority="sys:dept:delete">
            <a onClick={() => this.handleDelete(record.id)}>删除</a>
          </Authorized>
        </Fragment>
      ),
    },
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({ type: 'dept/fetchAll' });
  }

  /**搜索 */
  handleSearch = e => {
    e.preventDefault();

    const { dispatch, form } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;

      this.setState({ formValues: fieldsValue });
      dispatch({ type: 'dept/fetchAll', payload: fieldsValue });
    });
  };

  /**重置 */
  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState({ formValues: {} });

    dispatch({ type: 'dept/fetchAll' });
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
      type: 'dept/saveObj',
      payload: formatParams(fields),
      callback: () => {
        this.handleModalVisible();
        dispatch({ type: 'dept/fetchAll', payload: formValues });
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
          type: 'dept/deleteById',
          payload: id,
          callback: () => dispatch({ type: 'dept/fetchAll', payload: formValues }),
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
      dept: { allDepts },
      loading,
    } = this.props;
    const { selectedRows, modalVisible, editFormValues } = this.state;
    const data = { list: allDepts, pagination: null };

    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>{this.renderSearchForm()}</div>
            <div className={styles.tableListOperator}>
              <Authorized authority="sys:dept:save">
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
              pagination={false}
              onSelectRow={this.handleSelectRows}
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
    const { form, modalVisible, handleModalVisible, values, allDepts } = this.props;
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
                rules: [{ required: true, max: 10, message: '请输入部门名称(最大10个字符)' }],
                initialValue: values.name || '',
              })(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="编码" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {operation === 'add' &&
                form.getFieldDecorator('code', {
                  rules: [
                    { required: true, min: 4, max: 10, message: '请输入部门编码(4-10个字符)' },
                    {
                      validator: (rule, val, callback) => {
                        if (!val || val.length < 4) callback();
                        else {
                          validateCodeUnique(val).then(response => {
                            if (response.data === false) callback('部门编码已经存在');

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
            <FormItem label="上级部门" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('parentId', {
                rules: [{ required: true, message: '请选择上级部门' }],
                initialValue: values.parentId,
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
            <FormItem label="描述" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('description', {
                rules: [{ max: 50, message: '请输入部门编码(最大50个字符)' }],
                initialValue: values.description,
              })(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
        </Row>
      </Modal>
    );
  }
}

export default DeptPage;
