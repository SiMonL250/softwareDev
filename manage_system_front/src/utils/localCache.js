function localCache() {
  this.setCache = function (key, value) {
    window.localStorage.setItem(key, JSON.stringify(value));
  };

  this.getCache = function (key) {
    const value = window.localStorage.getItem(key);
    if (value) {
      return JSON.parse(value);
    }
  };

  this.deleteCache = function (key) {
    window.localStorage.removeItem(key);
  };

  this.clearCache = function () {
    window.localStorage.clear();
  };
}

export default new localCache();
