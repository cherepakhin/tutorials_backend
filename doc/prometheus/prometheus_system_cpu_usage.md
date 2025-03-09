### Как работать с Prometheus

Prometheus запущен на 192.168.1.20:9090

Перейти на [http://192.168.1.20:9090](http://192.168.1.20:9090)
В меню "Graph":  
1) В "Expression" ввести system_cpu_usage 
2) Перейти на Graph
3) Отрегулировать период (н.п. 15 мин.)

Итоговый запрос: [http://192.168.1.20:9090/graph?g0.range_input=15m&g0.expr=system_cpu_usage&g0.tab=0](http://192.168.1.20:9090/graph?g0.range_input=15m&g0.expr=system_cpu_usage&g0.tab=0)

![prometheus_system_cpu_usage](doc/prometheus/prometheus_system_cpu_usage.png)