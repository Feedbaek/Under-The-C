import axios from "axios";

//const URI = "http://localhost:4000/posts";
const URI = `https://${process.env.REACT_APP_SPRING_HOST}:${process.env.REACT_APP_SPRING_PORT}/evaluation/add`;

export const lectorePost = async (data: any) => {
    data.rating = parseInt(data.rating);
    const res = await axios.post(URI, data, {withCredentials: true});
    return res.data;
  };
