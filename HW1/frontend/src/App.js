import "./App.css";
import MainCard from "./components/mainCard/mainCard";

function App() {
  function Content() {
    return (
      <div style={{ width: "100%", backgroundColor: "#D6E4E5" }}>
        <MainCard />
      </div>
    );
  }

  return <Content />;
}

export default App;
