/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vaadin.highcharts;

/**
 *
 * @author Dobri
 */
public enum ChartType {

    //<editor-fold defaultstate="collapsed" desc="BAR">
    BAR(
            " chart: { "
            + "  type: 'bar' "
            + "},"
            + " tooltip: { "
            + "        }, "
            + "        plotOptions: { "
            + "            bar: { "
            + "                dataLabels: { "
            + "                    enabled: true "
            + "                } "
            + "            } "
            + "        }, "
            + "        credits: { "
            + "            enabled: false "
            + "        },"
    ),
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="STACKED BAR">
    STACKED_BAR(
            " chart: { "
            + "  type: 'bar' "
            + "}, "
            + " plotOptions: { "
            + "            series: { "
            + "                stacking: 'normal' "
            + "            } "
            + "        }, "
            + "        credits: { "
            + "            enabled: false "
            + "        },"
    ),
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="STACKED_COLUMN">
    STACKED_COLUMN(
            " chart: { "
            + "         type: 'column' "
            + "  }, "
            + "  tooltip: { "
            + "         shared: true, "
            + "         valueSuffix: '' "
            + "  }, "
            + "  plotOptions: { "
            + "            column: { "
            + "                stacking: 'normal', "
            + "                dataLabels: { "
            + "                    enabled: false, "
            + "                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white', "
            + "                    style: { "
            + "                        textShadow: '0 0 3px black' "
            + "                    } "
            + "                } "
            + "            } "
            + "  },"
            + "        credits: { "
            + "            enabled: false "
            + "        },"
    ),
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="LINE">
    LINE(
            "        yAxis: { "
            + "            title: { "
            + "                text: 'Ammounts' "
            + "            }, "
            + "            plotLines: [{ "
            + "                value: 0, "
            + "                width: 1, "
            + "                color: '#808080' "
            + "            }] "
            + "        }, "
            + "        tooltip: { "
            + "            valueSuffix: '' "
            + "        }, "
            + "        credits: { "
            + "            enabled: false "
            + "        },"
    ),
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="LINE_TIMESERIES_ZUMABLE">
    LINE_TIMESERIES_ZUMABLE(
            "chart: {"
            + "         zoomType: 'x'"
            + "     }, "
            + "            plotOptions: { "
            + "                area: { "
            + "                    fillColor: { "
            + "                        linearGradient: { "
            + "                            x1: 0, "
            + "                            y1: 0, "
            + "                            x2: 0, "
            + "                            y2: 1 "
            + "                        }, "
            + "                        stops: [ "
            + "                            [0, Highcharts.getOptions().colors[0]], "
            + "                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')] "
            + "                        ] "
            + "                    }, "
            + "                    marker: { "
            + "                        radius: 2 "
            + "                    }, "
            + "                    lineWidth: 1, "
            + "                    states: { "
            + "                        hover: { "
            + "                            lineWidth: 1 "
            + "                        } "
            + "                    }, "
            + "                    threshold: null "
            + "                } "
            + "            },"
            + "        credits: { "
            + "            enabled: false "
            + "        },"
    ),
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SPLINE">
    SPLINE(
            "chart: { "
            + "            type: 'spline' "
            + "        }, "
            + "        tooltip: { "
            + "            shared: true, "
            + "            valueSuffix: '' "
            + "        }, "
            + "        plotOptions: { "
            + "            spline: { "
            + "                marker: { "
            + "                    enabled: true "
            + "                } "
            + "            } "
            + "        }, "
            + "        credits: { "
            + "            enabled: false "
            + "        },"
    ),
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AREA">
    AREA(
            " chart: { "
            + "            type: 'area' "
            + "        },"
            + " plotOptions: { "
            + "            area: { "
            + "                marker: { "
            + "                    enabled: false, "
            + "                    symbol: 'circle', "
            + "                    radius: 2, "
            + "                    states: { "
            + "                        hover: { "
            + "                            enabled: true "
            + "                        } "
            + "                    } "
            + "                } "
            + "            } "
            + "        }, "
            + "        credits: { "
            + "            enabled: false "
            + "        },"
    ),
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AREA_SPLINE">
    AREA_SPLINE(
            "chart: { "
            + "    type: 'areaspline' "
            + " }, "
            + "        yAxis: { "
            + "            title: { "
            + "                text: ' ' "
            + "            } "
            + "        }, "
            + "        tooltip: { "
            + "            shared: true, "
            + "            valueSuffix: '' "
            + "        }, "
            + "        credits: { "
            + "            enabled: false "
            + "        },"
            + "        plotOptions: { "
            + "             areaspline: { "
            + "                 fillOpacity: 0.2 "
            + "             } "
            + "        }, "
    ),
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AREA_RANGE">
    AREA_RANGE(
            " chart: { "
            + "     type: 'arearange', "
            + "     zoomType: 'x'"
            + "}, "
            + "        credits: { "
            + "            enabled: false "
            + "        },"
    ),
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PIE_GRADIENT">
    PIE_GRADIENT(
            " chart: { "
            + "            plotBackgroundColor: null, "
            + "            plotBorderWidth: null, "
            + "            plotShadow: true, "
            + "            type: 'pie' "
            + "        }, "
            + " plotOptions: { "
            + "            pie: { "
            + "                allowPointSelect: true, "
            + "                cursor: 'pointer', "
            + "                dataLabels: { "
            + "                    enabled: true, "
            + "                    format: '<b>{point.name}</b>: {point.percentage:.2f} %', "
            + "                    style: { "
            + "                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black' "
            + "                    }, "
            + "                    connectorColor: 'silver' "
            + "                } "
            + "            } "
            + "        }, "
            + "        credits: { "
            + "            enabled: false "
            + "        },"
    ),
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PIE_GRADIENT">
    DONUT_3D(
            " chart: { "
            + "            type: 'pie', "
            + "            options3d: { "
            + "                enabled: true, "
            + "                alpha: 55 "
            + "            } "
            + "        }, "
            + "  plotOptions: { "
            + "            pie: { "
            + "                innerSize: 100, "
            + "                depth: 45 "
            + "            } "
            + "  }, "
            + "  credits: { "
            + "     enabled: false "
            + "  },"
    );
    //</editor-fold>

    private final String name;

    private ChartType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
