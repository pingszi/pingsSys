import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Row, Col, Card, Form, Input, Button } from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

const FormItem = Form.Item;

@connect(({ user }) => ({
  currentUser: user.currentUser,
}))
@Form.create()
class UpdatePasswordPage extends PureComponent {
  //**修改密码
  handleUpdatePassword = e => {
    e.preventDefault();
    const { form, dispatch, currentUser } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;

      const params = { ...fieldsValue, id: currentUser.id };
      dispatch({
        type: 'user/updatePassword',
        payload: params,
        callback: () => dispatch({ type: 'login/logout' }),
      });
    });
  };

  render() {
    const { form } = this.props;

    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <Row gutter={24}>
            <Col span={24}>
              <FormItem label="旧密码" labelCol={{ span: 8 }} wrapperCol={{ span: 8 }}>
                {form.getFieldDecorator('oldPassword', {
                  rules: [{ required: true, min: 4, max: 10, message: '请输入旧密码(4-10个字符)' }],
                })(<Input.Password placeholder="请输入旧密码" />)}
              </FormItem>
            </Col>
          </Row>
          <Row gutter={24}>
            <Col span={24}>
              <FormItem label="新密码" labelCol={{ span: 8 }} wrapperCol={{ span: 8 }}>
                {form.getFieldDecorator('password', {
                  rules: [{ required: true, min: 4, max: 10, message: '请输入新密码(4-10个字符)' }],
                })(<Input.Password placeholder="请输入新密码" />)}
              </FormItem>
            </Col>
          </Row>
          <Row gutter={24}>
            <Col span={24}>
              <FormItem label="确认密码" labelCol={{ span: 8 }} wrapperCol={{ span: 8 }}>
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
                })(<Input.Password placeholder="请输入确认密码" />)}
              </FormItem>
            </Col>
          </Row>
          <div style={{ overflow: 'hidden' }}>
            <div style={{ float: 'right', marginBottom: 24 }}>
              <Button type="primary" onClick={this.handleUpdatePassword}>
                确定
              </Button>
            </div>
          </div>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default UpdatePasswordPage;
