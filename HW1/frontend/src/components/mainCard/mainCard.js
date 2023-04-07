import { Card, Row } from "antd";
import React, { useState } from "react";
import NameSearch from "../Cards/NameSearch/NameSearch";

const tabList = [
  {
    key: "name",
    tab: "Name",
  },
  {
    key: "latlon",
    tab: "Latitude & Longitude",
  },
];

const contentList = {
  name: <NameSearch />,
  latlon: <p>content2</p>,
};
const MainCard = () => {
  const [activeTabKey1, setActiveTabKey1] = useState("name");

  const onTabChange = (key) => {
    setActiveTabKey1(key);
  };

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
          title="Search Method"
          style={{ backgroundColor: "#FAFAFA" }}
          tabList={tabList}
          activeTabKey={activeTabKey1}
          onTabChange={onTabChange}
        >
          {contentList[activeTabKey1]}
        </Card>
      </Row>
    </>
  );
};

export default MainCard;
