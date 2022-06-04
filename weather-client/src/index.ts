import axios from "axios";
import { deserialize } from "ts-byte-serializer";
import { WeatherData } from "./weather-data";

const get = async () => {
  const requests = [
    axios.get("http://localhost:8080/weather-json"),
    axios.get("http://localhost:8080/weather-binary", { responseType: "arraybuffer" }),
  ];
  const [jsonData, binaryData] = await Promise.all(requests);
  const jsonLoadedData = (jsonData as any).data;
  console.log("JSON", jsonLoadedData, "\n");

  console.log("Binary", (binaryData as any).data, "\n");
  const deserialized = deserialize(new Uint8Array((binaryData as any).data), WeatherData);
  // remove metadata and type-conversion
  const binaryLoadedData = {
    latitude: deserialized.latitude,
    longitude: deserialized.longitude,
    timestamp: new Date(Number(deserialized.ticks)),
    temperature: deserialized.temperature,
    humidity: deserialized.humidity,
  };

  console.log("Binary afer deserialization and type-conversion", binaryLoadedData);
};

get();
