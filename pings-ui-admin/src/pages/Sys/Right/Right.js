import React, { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import { Row, Col, Card, Form, Input, Button, Modal, Divider, TreeSelect, Select } from 'antd';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import { validateCodeUnique } from '@/services/sys/right';
import { formatParams } from '@/utils/utils';
import Authorized from '@/utils/Authorized';
import styles from './Right.less';

const FormItem = Form.Item;
const Option = Select.Option;

@connect(({ right, loading }) => ({
  right,
  loading: loading.models.right,
}))
@Form.create()
class RightPage extends PureComponent {
  state = {
    selectedRows: [], //**选中的行
    modalVisible: false, //**显示编译页面
    editFormValues: {}, //**编辑数据
  };

  /**table columns */
  columns = [
    { title: '编号', dataIndex: 'id', key: 'id' },
    { title: '编码', dataIndex: 'code' },
    { title: '名称', dataIndex: 'name' },
    {
      title: '类型',
      dataIndex: 'type',
      render: val => {
        let rst;
        switch (val) {
          case 1:
            rst = '系统';
            break;
          case 2:
            rst = '菜单';
            break;
          default:
            rst = '权限';
        }
        return rst;
      },
    },
    { title: '描述', dataIndex: 'description' },
    {
      title: '操作',
      render: (text, record) => (
        <Fragment>
          <Authorized authority="sys:right:save">
            <a onClick={() => this.handleModalVisible(true, record)}>修改</a>
          </Authorized>
          <Divider type="vertical" />
          <Authorized authority="sys:right:delete">
            <a onClick={() => this.handleDelete(record.id)}>删除</a>
          </Authorized>
        </Fragment>
      ),
    },
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({ type: 'right/fetchAll' });
  }

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

    dispatch({
      type: 'right/saveObj',
      payload: formatParams(fields),
      callback: () => {
        this.handleModalVisible();
        dispatch({ type: 'right/fetchAll' });
      },
    });
  };

  //**删除 */
  handleDelete = id => {
    const { dispatch } = this.props;

    Modal.confirm({
      title: '删除',
      content: '确定删除吗？',
      okText: '确认',
      cancelText: '取消',
      onOk: () =>
        dispatch({
          type: 'right/deleteById',
          payload: id,
          callback: () => dispatch({ type: 'right/fetchAll' }),
        }),
    });
  };

  render() {
    const {
      right: { allRights },
      loading,
    } = this.props;
    const { selectedRows, modalVisible, editFormValues } = this.state;
    const data = { list: allRights, pagination: null };

    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListOperator}>
              <Authorized authority="sys:right:save">
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
          allRights={allRights}
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
    const { form, modalVisible, handleModalVisible, values, allRights } = this.props;
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
                rules: [{ required: true, max: 10, message: '请输入权限名称(最大10个字符)' }],
                initialValue: values.name || '',
              })(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="编码" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {operation === 'add' &&
                form.getFieldDecorator('code', {
                  rules: [
                    { required: true, min: 3, max: 20, message: '请输入权限编码(3-20个字符)' },
                    {
                      validator: (rule, val, callback) => {
                        if (!val || val.length < 3) callback();
                        else {
                          validateCodeUnique(val).then(response => {
                            if (response.data === false) callback('权限编码已经存在');

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
            <FormItem label="上级权限" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('parentId', {
                rules: [{ required: true, message: '请选择上级权限' }],
                initialValue: values.parentId,
              })(
                <TreeSelect
                  style={{ width: 150 }}
                  width={80}
                  treeData={allRights}
                  placeholder="请选择"
                />
              )}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="类型" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('type', {
                rules: [{ required: true, message: '请选择类型' }],
                initialValue: values.type,
              })(
                <Select style={{ width: 150 }} placeholder="请选择">
                  <Option value={1}>系统</Option>
                  <Option value={2}>菜单</Option>
                  <Option value={3}>权限</Option>
                </Select>
              )}
            </FormItem>
          </Col>
        </Row>
        <Row gutter={24}>
          <Col span={12}>
            <FormItem label="描述" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('description', {
                rules: [{ max: 50, message: '请输入权限描述(最大50个字符)' }],
                initialValue: values.description,
              })(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
        </Row>
      </Modal>
    );
  }
}

export default RightPage;
