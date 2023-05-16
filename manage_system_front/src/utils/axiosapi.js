import axios from "axios";

class axiosApi {
    constructor(map, method, data, f) {
        (this.url = "http://localhost:8080"+map),

        (this.data = data),
        this.method = method,
        (this.useAxios = function () {
            axios({
                method: this.method,
                url: this.url,
                data: JSON.stringify(this.data),
                headers: { "Content-Type": "application/json" },
            })
                .then(f)
                .catch((e) => {
                    console.log(e);
                });
        });
    }
}

export default axiosApi;
