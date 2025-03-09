# Запуск Prometheus в docker ДЛЯ РУЧНЫХ ТЕСТОВ
#CHANGE ?????/prometheus.yml to FULL PATH!!!!!
docker run -d -p 9090:9090 -v ?????/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus