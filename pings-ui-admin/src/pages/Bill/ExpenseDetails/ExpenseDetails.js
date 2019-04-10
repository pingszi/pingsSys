import React, { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import { Row, Col, Card, Form, Input, Button, Modal, Divider, Select, DatePicker } from 'antd';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import moment from 'moment';

import { formatParams } from '@/utils/utils';
import Authorized from '@/utils/Authorized';
import styles from './ExpenseDetails.less';

const FormItem = Form.Item;
const Option = Select.Option;

@connect(({ expenseDetails, basData, loading }) => ({
  expenseDetails,
  typeBasDatas: basData.typeBasDatas,
  loading: loading.models.expenseDetails,
}))
@Form.create()
class ExpenseDetailsPage extends PureComponent {
  state = {
    selectedRows: [], //**选中的行
    formValues: {}, //**搜索数据
    modalVisible: false, //**显示编译页面
    editFormValues: {}, //**编辑数据
  };

  findPageUrl = 'expenseDetails/fetch'; //**分页查询

  saveUrl = 'expenseDetails/saveObj'; //**编辑

  deleteByIdUrl = 'expenseDetails/deleteById'; //**根据编号删除

  findStatusUrl = 'basData/fetchByType'; //**查询消费类型

  /**table columns */
  columns = [
    { title: '编号', dataIndex: 'id', key: 'id' },
    { title: '名称', dataIndex: 'name' },
    { title: '金额', dataIndex: 'value' },
    { title: '消费日期', dataIndex: 'expenseDate' },
    {
      title: '类型',
      dataIndex: 'typeId',
      render: val => {
        const { typeBasDatas } = this.props;
        const basData = typeBasDatas.find(b => b.id === val);
        return basData ? basData.name : '';
      },
    },
    { title: '描述', dataIndex: 'description' },
    {
      title: '操作',
      render: (text, record) => (
        <Fragment>
          <Authorized authority="bill:expenseDetails:save">
            <a onClick={() => this.handleModalVisible(true, record)}>修改</a>
          </Authorized>
          <Authorized authority="bill:expenseDetails:delete">
            <Divider type="vertical" />
            <a onClick={() => this.handleDelete(record.id)}>删除</a>
          </Authorized>
        </Fragment>
      ),
    },
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({ type: this.findPageUrl });
    dispatch({ type: this.findStatusUrl, payload: 'EXPENSE' });
  }

  /**搜索 */
  handleSearch = e => {
    e.preventDefault();

    const { dispatch, form } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;

      this.setState({ formValues: fieldsValue });
      dispatch({ type: this.findPageUrl, payload: fieldsValue });
    });
  };

  /**重置 */
  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState({ formValues: {} });

    dispatch({ type: this.findPageUrl });
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
    dispatch({ type: this.findPageUrl, payload: params });
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
      type: this.saveUrl,
      payload: formatParams(fields),
      callback: () => {
        this.handleModalVisible();
        dispatch({ type: this.findPageUrl, payload: formValues });
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
          type: this.deleteByIdUrl,
          payload: id,
          callback: () => dispatch({ type: this.findPageUrl, payload: formValues }),
        }),
    });
  };

  //**搜索表单*/
  renderSearchForm() {
    const {
      typeBasDatas,
      form: { getFieldDecorator },
    } = this.props;

    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={12} sm={24}>
            <FormItem label="类型">
              {getFieldDecorator('typeId')(
                <Select style={{ width: '100%' }} placeholder="请选择">
                  {typeBasDatas &&
                    typeBasDatas.map(basData => <Option key={basData.id}>{basData.name}</Option>)}
                </Select>
              )}
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
      typeBasDatas,
      expenseDetails: { data },
      loading,
    } = this.props;
    const { selectedRows, modalVisible, editFormValues } = this.state;

    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>{this.renderSearchForm()}</div>
            <div className={styles.tableListOperator}>
              <Authorized authority="bill:expenseDetails:save">
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
          typeBasDatas={typeBasDatas}
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
    const { form, modalVisible, handleModalVisible, values, typeBasDatas } = this.props;
    const expenseDate = values.expenseDate ? moment(values.expenseDate) : null;
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
                rules: [{ required: true, max: 20, message: '请输入名称(最大20个字符)' }],
                initialValue: values.name || '',
              })(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="金额" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('value', {
                rules: [
                  {
                    required: true,
                    type: 'number',
                    transform: value => (value ? Number.parseFloat(value) : value),
                    message: '请输入金额(0-999999.99)',
                    max: 999999.99,
                  },
                ],
                initialValue: values.value,
              })(<Input type="number" placeholder="请输入" />)}
            </FormItem>
          </Col>
        </Row>
        <Row gutter={24}>
          <Col span={12}>
            <FormItem label="类型" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('typeId', {
                rules: [{ required: true, message: '请选择类型' }],
                initialValue: values.typeId,
              })(
                <Select style={{ width: '100%' }} placeholder="请选择">
                  {typeBasDatas &&
                    typeBasDatas.map(basData => (
                      <Option key={basData.id} value={basData.id}>
                        {basData.name}
                      </Option>
                    ))}
                </Select>
              )}
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem label="消费日期" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('expenseDate', {
                rules: [{ required: true, message: '请选择类型' }],
                initialValue: expenseDate,
              })(<DatePicker format="YYYY-MM-DD" />)}
            </FormItem>
          </Col>
        </Row>
        <Row gutter={24}>
          <Col span={12}>
            <FormItem label="描述" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              {form.getFieldDecorator('description', { initialValue: values.description || '' })(
                <Input placeholder="请输入" />
              )}
            </FormItem>
          </Col>
        </Row>
      </Modal>
    );
  }
}

export default ExpenseDetailsPage;
