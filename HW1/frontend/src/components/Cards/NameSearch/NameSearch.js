import { InfoCircleOutlined } from "@ant-design/icons";
import { Form, Space, Input, Button, Table } from "antd";
import React, { useState } from "react";
import axios from "axios";

const NameSearch = () => {
  const [form] = Form.useForm();
  const [dataSource, setDataSource] = useState([]);

  const columns = [
    // air quality traits
    {
      key: 1,
      title: "Name",
      dataIndex: "name",
      className: "custom-row",
    },
    {
      key: 2,
      title: "Country",
      dataIndex: "country",
    },
    {
      key: 3,
      title: "Latitude",
      dataIndex: "latitude",
    },
    {
      key: 4,
      title: "Longitude",
      dataIndex: "longitude",
    },
    {
      key: 5,
      title: "CO",
      dataIndex: "co",
    },

    {
      key: 6,
      title: "NO2",
      dataIndex: "no2",
    },
    {
      key: 7,
      title: "O3",
      dataIndex: "o3",
    },
    {
      key: 8,
      title: "SO2",
      dataIndex: "so2",
    },
    {
      key: 9,
      title: "PM2_5",
      dataIndex: "pm2_5",
    },
    {
      key: 10,
      title: "PM10",
      dataIndex: "pm10",
    },
  ];

  const onReset = () => {
    form.resetFields();
    setDataSource([]);
  };

  // Função de tratamento dos dados do forms submetido
  const onFinish = (values) => {
    axios
      .get("http://localhost:8080/city/" + values.Name.replace(/ /g, ""))
      .then((res) => {
        return res;
      })
      .then((res) => {
        setDataSource([res.data]);
      });
  };

  return (
    <Space direction="vertical" style={{ alignItems: "center" }}>
      <Form form={form} layout="vertical" onFinish={onFinish}>
        <Space direction="vertical" style={{ alignItems: "center" }}>
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
      <Table
        columns={columns}
        dataSource={dataSource}
        pagination={false}
        style={{ color: "#8da6a8" }}
      ></Table>
    </Space>
  );
};

export default NameSearch;
