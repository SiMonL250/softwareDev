import localCache from "@/utils/localCache";
import { defineStore } from "pinia";
const defaultList = {
  admin: ["1", "2", "3"],
  teacher: ["2", "3"],
  student: ["3"],
};
export const userPermission = defineStore("permiss", {
  state: () => {
    const role = localCache.getCache("role");
    return {
      role: role ? role : "", //userprilege
      show: role == "student" ? false : true,
      key: role ? defaultList[role] : [], //permiss list;
    };
  },
  actions: {
    handleSet(val) {
      this.role = val;
      this.key = defaultList[val];
      if (val == "student") {
        this.show = false;
      } else {
        this.show = true;
      }
    },
    gethandle() {
      console.log("userPermission", this.show);
    },
  },
});
