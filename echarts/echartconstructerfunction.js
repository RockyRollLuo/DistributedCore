var CHARTTYPE_LineSimple = 0;
var CHARTTYPE_LineMaker = 1;
var CHARTTYPE_BubbleGradient = 2;

var DCD = 0;  //distributed core decomposition
var DECD = 1; //distributed eta core decomposition
var CD = 2;  // core decomposition
var ECD = 3; // eta core decomposition


var mytheme = 'light';
var chartcontainer = 'echartBg';

/**
 * main funcion
 * @param algorithm
 * @param dataset
 * @param charttype
 */

function showAllcharts(algorithm, dataset, charttype) {


    var datasourceroot = algorithm + "_" + dataset;
    // var jsonobject1 = datasourceroot + "";


    $.ajax({
        url: "data/Eachroundthecorenessdistribution_20190118222505.json",
        success: function (data) {

            showscattersingleaxis(data);

        }
    });
}

/**
 * line-simple
 * @param jsondata
 */
function showeLineSimple(jsondata) {
    //set value
    var xdata = jsondata.xdata;
    var ydata = jsondata.ydata;
    var myTitle = jsondata.myTitle;

    var maintitle = myTitle.split("_")[0];
    var subtitle = myTitle.split("_")[1];

    //
    var dom = document.getElementById(chartcontainer);
    var myChart = echarts.init(dom, mytheme);
    var app = {};
    option = null;
    option = {
        title: {
            text: maintitle,
            subtext: subtitle
        },
        legend: {
            data: ['maxCore', 'minCore']
        },
        xAxis: {
            type: 'category',
            data: xdata
        },
        yAxis: {
            type: 'value'
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [{
            data: ydata,
            type: 'line'
        }]
    };

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}

/**
 * line maker
 * @param jsondata
 */
function showLineMaker(jsondata) {

    var myTitle = jsondata.myTitle
    var xdata = jsondata.xdata;
    var ydata = jsondata.ydata;


    var dom = document.getElementById(chartcontainer);
    var myChart = echarts.init(dom, mytheme);
    var app = {};
    option = null;
    option = {
        title: {
            text: myTitle,
            subtext: 'core number distribution'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['maxCore', 'minCore']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: xdata
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value} °C'
            }
        },
        series: [
            {
                name: '最高气温',
                type: 'line',
                data: [11, 11, 15, 13, 12, 13, 10],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name: '最低气温',
                type: 'line',
                data: [1, -2, 2, 5, 3, 2, 0],
                markPoint: {
                    data: [
                        {name: '周最低', value: -2, xAxis: 1, yAxis: -1.5}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'},
                        [{
                            symbol: 'none',
                            x: '90%',
                            yAxis: 'max'
                        }, {
                            symbol: 'circle',
                            label: {
                                normal: {
                                    position: 'start',
                                    formatter: '最大值'
                                }
                            },
                            type: 'max',
                            name: '最高点'
                        }]
                    ]
                }
            }
        ]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }

}

function showBubbleGradient(jsondata) {
    //set value

    //
    var dom = document.getElementById(chartcontainer);
    var myChart = echarts.init(dom, mytheme);
    var app = {};
    option = null;
    app.title = '气泡图';

    var data = [
        [[28604, 77, 17096869, 'Australia', 1990], [31163, 77.4, 27662440, 'Canada', 1990], [1516, 68, 1154605773, 'China', 1990], [13670, 74.7, 10582082, 'Cuba', 1990], [28599, 75, 4986705, 'Finland', 1990], [29476, 77.1, 56943299, 'France', 1990], [31476, 75.4, 78958237, 'Germany', 1990], [28666, 78.1, 254830, 'Iceland', 1990], [1777, 57.7, 870601776, 'India', 1990], [29550, 79.1, 122249285, 'Japan', 1990], [2076, 67.9, 20194354, 'North Korea', 1990], [12087, 72, 42972254, 'South Korea', 1990], [24021, 75.4, 3397534, 'New Zealand', 1990], [43296, 76.8, 4240375, 'Norway', 1990], [10088, 70.8, 38195258, 'Poland', 1990], [19349, 69.6, 147568552, 'Russia', 1990], [10670, 67.3, 53994605, 'Turkey', 1990], [26424, 75.7, 57110117, 'United Kingdom', 1990], [37062, 75.4, 252847810, 'United States', 1990]],
        [[44056, 81.8, 23968973, 'Australia', 2015], [43294, 81.7, 35939927, 'Canada', 2015], [13334, 76.9, 1376048943, 'China', 2015], [21291, 78.5, 11389562, 'Cuba', 2015], [38923, 80.8, 5503457, 'Finland', 2015], [37599, 81.9, 64395345, 'France', 2015], [44053, 81.1, 80688545, 'Germany', 2015], [42182, 82.8, 329425, 'Iceland', 2015], [5903, 66.8, 1311050527, 'India', 2015], [36162, 83.5, 126573481, 'Japan', 2015], [1390, 71.4, 25155317, 'North Korea', 2015], [34644, 80.7, 50293439, 'South Korea', 2015], [34186, 80.6, 4528526, 'New Zealand', 2015], [64304, 81.6, 5210967, 'Norway', 2015], [24787, 77.3, 38611794, 'Poland', 2015], [23038, 73.13, 143456918, 'Russia', 2015], [19360, 76.5, 78665830, 'Turkey', 2015], [38225, 81.4, 64715810, 'United Kingdom', 2015], [53354, 79.1, 321773631, 'United States', 2015]]
    ];

    option = {
        backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
            offset: 0,
            color: '#f7f8fa'
        }, {
            offset: 1,
            color: '#cdd0d5'
        }]),
        title: {
            text: '1990 与 2015 年各国家人均寿命与 GDP'
        },
        legend: {
            right: 10,
            data: ['1990', '2015']
        },
        xAxis: {
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            }
        },
        yAxis: {
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            },
            scale: true
        },
        series: [{
            name: '1990',
            data: data[0],
            type: 'scatter',
            symbolSize: function (data) {
                return Math.sqrt(data[2]) / 5e2;
            },
            label: {
                emphasis: {
                    show: true,
                    formatter: function (param) {
                        return param.data[3];
                    },
                    position: 'top'
                }
            },
            itemStyle: {
                normal: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(120, 36, 50, 0.5)',
                    shadowOffsetY: 5,
                    color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                        offset: 0,
                        color: 'rgb(251, 118, 123)'
                    }, {
                        offset: 1,
                        color: 'rgb(204, 46, 72)'
                    }])
                }
            }
        }, {
            name: '2015',
            data: data[1],
            type: 'scatter',
            symbolSize: function (data) {
                return Math.sqrt(data[2]) / 5e2;
            },
            label: {
                emphasis: {
                    show: true,
                    formatter: function (param) {
                        return param.data[3];
                    },
                    position: 'top'
                }
            },
            itemStyle: {
                normal: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(25, 100, 150, 0.5)',
                    shadowOffsetY: 5,
                    color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                        offset: 0,
                        color: 'rgb(129, 227, 238)'
                    }, {
                        offset: 1,
                        color: 'rgb(25, 183, 207)'
                    }])
                }
            }
        }]
    };

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}


/**
 * each round the coreness distribution
 * @param jsondata
 */
function showscattersingleaxis(jsondata) {

    var coreness=jsondata.myData.ydata;
    var round=jsondata.myData.xdata;
    var datalist=jsondata.myData.zdata;

    var roundsize=round.length;

    var dom = document.getElementById("scattersingleaxis");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    app.title = 'Each round the estimate core distribution';

    var hours = coreness;
    var days =round;

    // round,core,num
    var data = datalist;
    option = {
        legend: [],
        tooltip: {
            position: 'top'
        },
        title: [],
        singleAxis: [],
        series: []
    };

    echarts.util.each(days, function (day, idx) {
        option.title.push({
            textBaseline: 'middle',
            top: (idx + 0.5) * 100 / roundsize + '%',
            text: day
        });
        option.singleAxis.push({
            left: 10,
            type: 'category',
            boundaryGap: false,
            data: hours,
            top: (idx * 100 / roundsize + 5) + '%',
            height: (100 / roundsize - 10) + '%',
            axisLabel: {
                interval: 2
            }
        });
        option.series.push({
            singleAxisIndex: idx,
            coordinateSystem: 'singleAxis',
            type: 'scatter',
            data: [],
            symbolSize: function (dataItem) {
                return dataItem[1] * 1;
            }
        });
    });

    echarts.util.each(data, function (dataItem) {
        option.series[dataItem[0]].data.push([dataItem[1], dataItem[2]]);
    });
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}














/**
 * dataset info
 */
function showdatasetinfo(datasetname) {
    //TODO:create json for every dataset

    var dom = document.getElementById("datasetinfo");
    var myChart = echarts.init(dom,mytheme);
    myChart.showLoading();
    option=null;
    $.get('datasetinfo/graphjson.json', function (webkitDep) {
        myChart.hideLoading();

        option = {
            toolbox: {
                show: true,
                feature: {
                    dataView: {readOnly: false},
                    saveAsImage: {}
                }
            },
            legend: {
                data: ['1-coreness', '2-coreness', '3-coreness', '4-coreness', '5-coreness']
            },
            series: [{
                type: 'graph',
                layout: 'force',
                animation: false,
                label: {
                    normal: {
                        position: 'right',
                        formatter: '{b}'
                    }
                },
                draggable: true,
                data: webkitDep.nodes.map(function (node, idx) {
                    node.id = idx;
                    return node;
                }),
                categories: webkitDep.categories,
                force: {
                    initLayout: 'circular',
                    edgeLength: 6,
                    repulsion: 20,
                    gravity: 0.2
                },
                edges: webkitDep.links
            }]
        };

        myChart.setOption(option);
    });
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }

}