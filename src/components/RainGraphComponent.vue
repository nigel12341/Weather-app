<template>
  <h2>Rain per hour</h2>
<div class="rainChart">
  <canvas id="acquisitions"></canvas>
</div>
</template>

<script lang="ts">
import {Chart} from 'chart.js/auto';
import ChartDataLabels from 'chartjs-plugin-datalabels';

export default {
  name: "RainGraphComponent",
  data() {
    return {
      weatherMessage: [] as any,
      ip: '' as any,
    }
  },
  async mounted() {
    await this.getWeatherMessage();
  },

  methods: {
    async getIpAdress() {
      let response = await fetch('https://api.ipify.org?format=json');
      let data = await response.json();
      return data.ip
    },
    async CreateBarGraph() {
      Chart.register(ChartDataLabels);
      const apiData = this.weatherMessage;
      let data = [];
      for(let key in apiData) {
        data.push({
          hour: apiData[key].time.slice(11, 13),
          mmRain: apiData[key].precip_mm
        })
      }

        new Chart(
            document.getElementById('acquisitions') as HTMLCanvasElement,
            {
              type: 'line',
              data: {
                labels: data.map(row => row.hour),
                datasets: [
                  {
                    label: 'MM of rain per hour',
                    data: data.map(row => row.mmRain),
                    fill: true,
                    tension: 0.5,
                    datalabels: {
                      align: 'top',
                      formatter: function(value: any) {
                        if(value > 0) {
                          return value + ' mm';
                        }
                        return null;
                      },
                      offset: -2,
                    }
                  }
                ]
              },
              options:{
                layout:{
                  padding: {
                    top: 10,
                    bottom: 10,
                    left: 10,
                    right: 10
                  }
                },
                maintainAspectRatio: false,
                scales: {
                  x: {
                    grid: {
                      display: false,
                    },
                  },
                  y: {
                    ticks:{
                      display: false
                    },
                    grid: {
                      display: false,
                    },

                  }
                },
                responsive: true,
                plugins: {
                  legend: {
                    display: false,
                  },
                },
                elements: {
                  point: {
                    radius: 0
                  }
                },
                events: ['click'],
              },
            }
        );
    },
    async getWeatherMessage() {
      if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(async position => {
          await fetch(import.meta.env.VITE_URL_API + '/v1/weather/forecast?' + new URLSearchParams({
            location: position.coords.latitude + ',' + position.coords.longitude,
            days: '1'
          })).then(response => response.json()).then(data => {
            this.weatherMessage = data.forecast.forecastday[0].hour
            this.CreateBarGraph();
          }).catch(() => {
            this.loadingStatus = 'error'
          })
        }, async () => {

          await fetch(import.meta.env.VITE_URL_API + '/v1/weather/forecast?' + new URLSearchParams({
            location: await this.getIpAdress(),
            days: '1'
          })).then(response => response.json()).then(data => {
            this.weatherMessage = data.forecast.forecastday[0].hour
          }).catch(() => {
            this.loadingStatus = 'error'
          })
        });
      }
    },


  }
}
</script>

<style scoped>
.rainChart{
  max-width: 1320px;
  margin: 0 auto;
}
</style>