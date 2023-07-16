import axios from "axios";

//const URI = "http://localhost:4000/posts";
const URI = "http://115.85.181.92:8090//lecture";

export const lectorePost = async (data: any) => {
    data.rating = parseInt(data.rating);
    const res = await axios.post(URI, data);
    return res.data;
  };