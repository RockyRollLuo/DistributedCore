var CHARTTYPE_LineSimple = 0;
var CHARTTYPE_LineMaker = 1;
var CHARTTYPE_BubbleGradient = 2;

var DCD = 0;  //distributed core decomposition
var DECD = 1; //distributed eta core decomposition
var CD = 2;  // core decomposition
var ECD = 3; // eta core decomposition

//ConstVal
var CHART_eachRoundCorenessDistribution = 1;


var mytheme = 'light';
var chartcontainer = 'echartBg';

/**
 * main funcion
 * @param algorithm
 * @param dataset
 */

function showAllcharts(algorithm, dataset) {
    var filenameprefix = "" + algorithm + dataset;

    //1
    $.ajax({
        url: "data/" + filenameprefix + "1.json",
        success: function (data) {
            var charttype = "chart1";
            showeLineSimple(data, charttype);

        }
    });

    //2
    $.ajax({
        url: "data/" + filenameprefix + "2.json",
        success: function (data) {
            var charttype = "chart2";
            showeLineSimple(data, charttype);

        }
    });

    //3
    $.ajax({
        url: "data/" + filenameprefix + "3.json",
        success: function (data) {
            var charttype = "chart3";
            showPieLegend(data, charttype);

        }
    });

    //4
    $.ajax({
        url: "data/" + filenameprefix + "4.json",
        success: function (data) {
            var charttype = "chart4";
            showBarGradient(data, charttype);

        }
    });

    //6
    $.ajax({
        url: "data/" + filenameprefix + "6.json",
        success: function (data) {
            var charttype = "chart6";
            showBarGradient(data, charttype);

        }
    });

    //9
    $.ajax({
        url: "data/" + filenameprefix + "9.json",
        success: function (data) {
            var charttype = "chart9";
            showscattersingleaxis(data, charttype);

        }
    });

}

/**
 * line-simple
 * @param jsondata
 */
function showeLineSimple(jsondata, charttype) {
    //set value
    var xdata = jsondata.myData.xdata;
    var ydata = jsondata.myData.ydata;


    var dom = document.getElementById(charttype);
    var myChart = echarts.init(dom, mytheme);
    var app = {};
    option = null;
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
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
            name: "num",
            data: ydata,
            type: 'line'
        }]
    };

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}


/**
 * pie-legend
 * @param jsondata
 * @param charttype
 */
function showPieLegend(jsondata, charttype) {
    var legendData=jsondata.myData.myLegendData; //["1-coreness","2-coreness",...]
    var selectedData=jsondata.myData.mySelectedData; //{"1-coreness":true,...}
    var seriesData=jsondata.myData.mySeriesData; //[{"name":"1-coreness","value":20}]


    var dom = document.getElementById(charttype);
    var myChart = echarts.init(dom);
    var app = {};
    option = null;

    option = {
        toolbox: {
            show: true,
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            type: 'scroll',
            orient: 'vertical',
            right: 10,
            top: 20,
            bottom: 20,
            data: legendData,

            selected: selectedData
        },
        series: [
            {
                name: 'coreness',
                type: 'pie',
                radius: '55%',
                center: ['40%', '50%'],
                data: seriesData,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }

}

/**
 * 4,6
 * @param jsondata
 * @param charttype
 */
function showBarGradient(jsondata,charttype) {
    var xdata=jsondata.myData.xdata;
    var ydata=jsondata.myData.ydata;
    var maxValue=jsondata.myData.maxNum;


    var dom = document.getElementById(charttype);
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    var dataAxis = xdata;
    var data = ydata;
    var yMax = maxValue;
    var dataShadow = [];

    for (var i = 0; i < data.length; i++) {
        dataShadow.push(yMax);
    }

    option = {
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
            data: dataAxis,
            axisLabel: {
                inside: true,
                textStyle: {
                    color: '#fff'
                }
            },
            axisTick: {
                show: true
            },
            axisLine: {
                show: false
            },
            z: 10
        },
        yAxis: {
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#999'
                }
            }
        },
        dataZoom: [
            {
                type: 'inside'
            }
        ],
        series: [
            { // For shadow
                type: 'bar',
                itemStyle: {
                    normal: {color: 'rgba(0,0,0,0.05)'}
                },
                barGap: '-100%',
                barCategoryGap: '40%',
                data: dataShadow,
                animation: false
            },
            {
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#83bff6'},
                                {offset: 0.5, color: '#188df0'},
                                {offset: 1, color: '#188df0'}
                            ]
                        )
                    },
                    emphasis: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#2378f7'},
                                {offset: 0.7, color: '#2378f7'},
                                {offset: 1, color: '#83bff6'}
                            ]
                        )
                    }
                },
                data: data
            }
        ]
    };

    // Enable data zoom when user click bar.
    var zoomSize = 6;
    myChart.on('click', function (params) {
        console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
        myChart.dispatchAction({
            type: 'dataZoom',
            startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
            endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
        });
    });
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }

}



/**
 * each round the coreness distribution
 * @param jsondata
 */
function showscattersingleaxis(jsondata, charttype) {

    var coreness = jsondata.myData.ydata;
    var round = jsondata.myData.xdata;
    var datalist = jsondata.myData.zdata;

    var roundsize = round.length;
    var xdata = coreness;
    var ydata = round;
    var maxvalue = datalist[0][2];

    var dom = document.getElementById(charttype);
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    app.title = 'Each round the estimate core distribution';
    // round,core,num
    option = {
        legend: [],
        tooltip: {
            position: 'top'
        },
        title: [],
        singleAxis: [],
        series: []
    };

    echarts.util.each(ydata, function (day, idx) {
        option.title.push({
            textBaseline: 'middle',
            top: ((idx) * 100 / roundsize + 5) + '%',
            text: day
        });
        option.singleAxis.push({
            left: 150,
            type: 'category',
            boundaryGap: false,
            data: xdata,
            top: (idx * 100 / roundsize + 5) + '%',
            height: '1%',
            axisLabel: {
                interval: 1                           //axis distance
            }
        });
        option.series.push({
            singleAxisIndex: idx,
            coordinateSystem: 'singleAxis',
            type: 'scatter',
            data: [],
            symbolSize: function (dataItem) {
                return dataItem[1] * 0.03;
            }
        });
    });

    echarts.util.each(datalist, function (dataItem) {
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

    var dom = document.getElementById("datasetinfo");
    var myChart = echarts.init(dom, mytheme);
    myChart.showLoading();
    option = null;
    $.get('datasetinfo/' + datasetname + '.json', function (webkitDep) {
        myChart.hideLoading();

        var mycategory = webkitDep.categories;
        var len = mycategory.length;
        var min = mycategory[0]["base"];

        var dataitem = new Array();
        var index = 0;
        for (var i = parseInt(min); i < len + 1; i++) {
            dataitem[index] = i + "-coreness";
            index++;
        }
        option = {
            toolbox: {
                show: true,
                feature: {
                    dataView: {readOnly: false},
                    saveAsImage: {}
                }
            },
            legend: {
                data: dataitem
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
                    edgeLength: 50,
                    repulsion: 40,
                    gravity: 0.5
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