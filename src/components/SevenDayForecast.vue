<template>
  <div v-if="weatherMessage.length > 0" class="total">
    <h3>7 Day Forecast</h3>
    <section class="container forecastList" >
      <div class="card" v-for="weather in weatherMessage">
        <div class="card-body">
          <h5 class="card-title">{{new Date(weather.date).toLocaleString('en-us', {weekday:'long'})}}</h5>
          <div class="card-text">
            <p class="maxTemp">{{ Math.round(weather.day.maxtemp_c) }} °C</p>
            <p>{{ Math.round(weather.day.mintemp_c) }} °C</p>
            <img :src="weather.day.condition.icon" alt="Weather image"/>
            <p>{{weather.day.daily_chance_of_rain}}%</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
export default {
  name: "SevenDayForecast",
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

    async getWeatherMessage() {
      if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(async (position) => {
          await fetch(import.meta.env.VITE_URL_API + '/v1/weather/forecast?' + new URLSearchParams({
            location: position.coords.latitude + ',' + position.coords.longitude,
            days: '7'
          })).then(response => response.json()).then(data => {
            this.weatherMessage = data.forecast.forecastday
          }).catch(() => {
            this.loadingStatus = 'error'
          })
        }, async () => {

          await fetch(import.meta.env.VITE_URL_API + '/v1/weather/forecast?' + new URLSearchParams({
            location: await this.getIpAdress(),
            days: '7'
          })).then(response => response.json()).then(data => {
            this.weatherMessage = data.forecast.forecastday
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
.forecastList {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}
.total {
  width: 100%;
}
.maxTemp {
  font-size: 1.5rem;
  padding-bottom: 0;
  margin-bottom: 0;
}
</style>