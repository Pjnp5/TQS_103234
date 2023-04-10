import { InfoCircleOutlined } from "@ant-design/icons";
import { Form, Space, Input, Button, Table } from "antd";
import React, { useState } from "react";
import axios from "axios";

const LatLonSearch = () => {
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
    console.log(values);
    const params = new URLSearchParams()
    params.append("lat", parseFloat(values.Latitude))
    params.append("lon", parseFloat(values.Longitude))

    axios
      .get(
        `http://localhost:8080/coords?${params}`
      )
      .then((res) => {
        return res;
      })
      .then((res) => {
        console.log(res.data);
        setDataSource([res.data]);
      });
  };

  return (
    <Space direction="vertical" style={{ alignItems: "center" }}>
      <Form form={form} layout="vertical" onFinish={onFinish}>
        <Space direction="vertical" style={{ alignItems: "center" }}>
          <Space direction="horizontal">
            <Form.Item
              label="Latitude"
              name="Latitude"
              rules={[
                {
                  required: true,
                  message:
                    "Please input the latitude of the city you want to search for!",
                },
              ]}
              tooltip={{
                title: "Latitude",
                icon: <InfoCircleOutlined />,
              }}
            >
              <Input name="Latitude" placeholder="38.72" allowClear={true} />
            </Form.Item>
            <Form.Item
              label="Longitude"
              name="Longitude"
              rules={[
                {
                  required: true,
                  message:
                    "Please input the longitude of the city you want to search for!",
                },
              ]}
              tooltip={{
                title: "Longitude",
                icon: <InfoCircleOutlined />,
              }}
            >
              <Input name="Longitude" placeholder="-9.13" allowClear={true} />
            </Form.Item>
          </Space>
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

export default LatLonSearch;
