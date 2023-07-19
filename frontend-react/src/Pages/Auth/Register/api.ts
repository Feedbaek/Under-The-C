import axios from "axios";
import { Member } from "./Member";
const BASE_URL = `http://${process.env.REACT_APP_SPRING_HOST}:${process.env.REACT_APP_SPRING_PORT}`;

export const memberPost = async (data: any) => {
  const URL = `${BASE_URL}/user/add`;
  console.log("URL:", URL);
  const res = await axios.post(URL, data, {withCredentials: true});
  return res.data;
};
