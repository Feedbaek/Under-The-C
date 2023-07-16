import axios from "axios";
//import Member from './Member';

//const URI = "http://localhost:4000/members";
const URI = "http://115.85.181.92:8090/user";

export const memberPost = async (data: any) => {
    const res = await axios.post(URI, data);
    return res.data;
  };