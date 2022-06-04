import {
  Uint8,
  SerializableClass,
  SerializableNumber,
  Uint32,
  Uint64,
  Serializable,
  AppendableByteStream,
} from "ts-byte-serializer";
import { Float32, Float64 } from "ts-byte-serializer/dist/serializable-primitives/serializable-number";

@SerializableClass({ littleEndian: false })
export class WeatherData implements Serializable {
  // Type (e.g. 1 Byte) | Length | Latitude | Longitude | Timestamp (as ticks) | Temperature | Humidity
  @SerializableNumber(Uint8)
  public type: number = 0;

  @SerializableNumber(Uint32)
  public length: number = 0;

  @SerializableNumber(Float64)
  public latitude: number = 0;

  @SerializableNumber(Float64)
  public longitude: number = 0;

  @SerializableNumber(Uint64)
  public ticks: bigint = BigInt(0);

  @SerializableNumber(Float32)
  public temperature: number = 0;

  @SerializableNumber(Float32)
  public humidity: number = 0;

  constructor(
    type: number = 0,
    length: number = 0,
    latitude: number = 0,
    longitude: number = 0,
    ticks: bigint = BigInt(0),
    temperature: number = 0,
    humidity: number = 0
  ) {}

  public init(
    type: number = 0,
    length: number = 0,
    latitude: number = 0,
    longitude: number = 0,
    ticks: bigint = BigInt(0),
    temperature: number = 0,
    humidity: number = 0
  ) {
    this.type = type;
    this.length = length;
    this.latitude = latitude;
    this.longitude = longitude;
    this.ticks = ticks;
    this.temperature = temperature;
    this.humidity = humidity;
  }

  serialize(): Uint8Array {
    throw new Error("Method not implemented.");
  }
  deserialize(bytes: AppendableByteStream | Uint8Array): void {
    throw new Error("Method not implemented.");
  }
}
