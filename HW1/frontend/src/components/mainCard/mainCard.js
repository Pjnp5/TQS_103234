import { Card, Row } from "antd";
import React, { useState, useEffect } from "react";
import LatLonSearch from "../Cards/LatLonSearch/LatLonSearch";
import NameSearch from "../Cards/NameSearch/NameSearch";
import axios from "axios";

const tabList = [
  {
    key: "name",
    tab: "Name",
  },
  {
    key: "latlon",
    tab: "Lat & Long",
  },
];

const contentList = {
  name: <NameSearch />,
  latlon: <LatLonSearch />,
};

const MainCard = () => {
  const [activeTabKey1, setActiveTabKey1] = useState("name");
  const onTabChange = (key) => {
    setActiveTabKey1(key);
  };

  const [dataSource, setDataSource] = useState({});

  const getCacheData = () => {
    axios
      .get("http://localhost:8080/cacheDetails")
      .then((res) => {
        return res;
      })
      .then((res) => {
        setDataSource(res.data);
      });
  };

  useEffect(() => {
    const intervalId = setInterval(getCacheData, 1000);

    return () => {
      clearInterval(intervalId);
    };
  }, []);

  getCacheData();
  return (
    <>
      {/* Colocar o Card que vai conter o tudo centrado vetical e horizontalmente */}
      <Row
        type="flex"
        justify="center"
        align="middle"
        style={{ minHeight: "100vh" }}
      >
        {/* Card com o resto dentro */}
        <Card
          title={"Search Method"}
          style={{ backgroundColor: "#FAFAFA", textAlign: "center" }}
          tabProps={{ centered: true }}
          tabList={tabList}
          activeTabKey={activeTabKey1}
          onTabChange={onTabChange}
          bodyStyle={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
          }}
          actions={[
            "Requests: " + dataSource.requests,
            "Hits: " + dataSource.hits,
            "Misses: " + dataSource.misses,
          ]}
        >
          {contentList[activeTabKey1]}
        </Card>
      </Row>
    </>
  );
};

export default MainCard;
