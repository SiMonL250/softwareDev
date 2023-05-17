import * as echarts from "echarts";
function initEtypeBar(data) {
  document.querySelector("#etype_bar").removeAttribute("_echarts_instance_");
  let chart = echarts.getInstanceByDom(document.getElementById("etype_bar"));
  if (chart == null) {
    chart = echarts.init(document.getElementById("etype_bar"), "dark");
    // 把配置和数据放这里

    chart.setOption({
      title: {
        text: "类型&状态统计" + "   总数:" + data.sum,
      },
      tooltip: {
        trigger: "axis",
        axisPointer: {
          type: "shadow",
        },
      },
      legend: {},
      grid: {
        left: "3%",
        right: "4%",
        bottom: "3%",
        containLabel: true,
      },
      yAxis: {
        type: "value",
        boundaryGap: [0, 0.01],
      },
      xAxis: {
        type: "category",
        data: data.etype,
      },
      series: [
        {
          name: "正常",
          type: "bar",
          data: data.normal,
        },
        {
          name: "损坏",
          type: "bar",
          data: data.broke,
        },
      ],
    });
  }
}

function setData(predata) {
  let data = [];
  let month_present = new Date().getMonth();
  //(month_present-i+12)%12+1  i个月前
  for (let i = 0; i < 6; i++) {
    let month = ((month_present - i + 12) % 12) + 1;
    data.push({
      month: month,
      count: 0,
    });
  }
  for (let i of predata) {
    for (let j of data) {
      if (i.month == j.month) j.count = i.count;
    }
  }

  let d = [],
    s = [];
  let len = data.length;
  for (let i = len - 1; i >= 0; i--) {
    d.push(data[i].month);
    s.push(data[i].count);
  }

  return { d, s };
}
function initLineCahrt(predata) {
  const data = setData(predata);

  document
    .querySelector("#increase_month")
    .removeAttribute("_echarts_instance_");
  let increase_month = document.getElementById("increase_month");
  let chart = echarts.getInstanceByDom(increase_month);

  if (chart == null) {
    chart = echarts.init(increase_month, "dark");
    chart.setOption({
      xAxis: {
        type: "category",
        data: data.d,
      },
      yAxis: {
        type: "value",
      },
      tooltip: {
        trigger: "axis",
        formatter: function (params) {
          params = params[0];
          let now = new Date();
          let year,
            month = data.d[params.dataIndex];
          if (month <= now.getMonth()) year = now.getFullYear();
          else year = now.getFullYear() - 1;
          return year + "/" + month + " : " + params.value;
        },
      },
      series: [
        {
          data: data.s,
          type: "line",
        },
      ],
    });
  }
}

function datap(d) {
  let legendd = [];
  let seriesd = [];

  for (let i of d) {
    legendd.push(i.name);
    seriesd.push(i.value);
  }

  return { legendd, seriesd };
}

function initMyApply(data, id, title) {
  var d = datap(data);
  document.getElementById(id).removeAttribute("_echarts_instance_");
  let my_apply = document.getElementById(id);
  let c = echarts.init(my_apply, "dark");

  c.setOption({
	title:{
		text:title,
		left:'center'
	},
    xAxis: {
      type: "category",
      data: d.legendd,
    },
    yAxis: {
      types: "value",
    },
    tooltip: {
      trigger: "axis",
    //   axisPointer: {
    //     type: "shadow",
    //   },
    },
    series: [{ data: d.seriesd, type: "bar" }],
  });
}

export { initEtypeBar, initLineCahrt, initMyApply };
