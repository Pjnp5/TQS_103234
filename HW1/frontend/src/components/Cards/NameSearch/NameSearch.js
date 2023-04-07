import { InfoCircleOutlined } from "@ant-design/icons";
import { Form, Space, Input, Button } from "antd";
import React from "react";
import axios from "axios";

const NameSearch = () => {
  const [form] = Form.useForm();

  const onReset = () => {
    console.log("Cona");
    form.resetFields();
  };

  // Função de tratamento dos dados do forms submetido
  const onFinish = (values) => {
    console.log(values);
    axios
      .get("http://localhost:8080/city/" + values.Name)
      .then((res) => {
        return res.data;
      })
      .then((res) => {
        console.log(res)
      });
  };
  return (
    <Form form={form} layout="vertical" onFinish={onFinish}>
      <Space direction="vertical">
        <Form.Item
          label="Name"
          name="Name"
          rules={[
            {
              required: true,
              message:
                "Please input the name of the city you want to search for!",
            },
          ]}
          tooltip={{
            title: "Name",
            icon: <InfoCircleOutlined />,
          }}
        >
          <Input name="Name" placeholder="Oporto" allowClear={true} />
        </Form.Item>
        <Form.Item style={{ justifyContent: "center" }}>
          <Space>
            <Button type="primary" htmlType="submit">
              Search
            </Button>
            <Button htmlType="button" onClick={onReset}>
              Reset
            </Button>
          </Space>
        </Form.Item>
      </Space>
    </Form>
    
  );
};

export default NameSearch;
