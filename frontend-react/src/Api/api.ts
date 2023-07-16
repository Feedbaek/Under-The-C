import axios from "axios";

const URI = "http://localhost:4000/posts";

export const fetchPost = async () => {
	const res = await axios.get(URI);
	return res.data;
}