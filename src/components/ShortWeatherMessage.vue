<template>
  <div v-if="weatherMessage" class="weatherSection">
    <h1>Welcome!</h1>
    <img :src="icon" alt="Weather Icon"/>
    <h3>{{weatherMessage}}</h3>
  </div>
  <div v-if='loadingStatus === "loading"' class="loading">
    <h1>Loading data...</h1>
  </div>
  <div v-if="loadingStatus === 'error'">
    <h1>Error loading weather data :(</h1>
  </div>

  <p>{{lastUpdated}}</p>
</template>

<script lang="ts">
export default {
  name: "ShortWeatherMessage",
  data() {
    return {
      weatherMessage: '' as any,
      ip: '' as any,
      icon: '' as any,
      lastUpdated: '' as any,
      loadingStatus: 'loading' as any,
    }
  },
  async created() {
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
          await fetch(import.meta.env.VITE_URL_API + '/v1/weather/current?' + new URLSearchParams({
            location: position.coords.latitude + ',' + position.coords.longitude
          })).then(response => response.json()).then(data => {
            if(data.status) {
              this.loadingStatus = 'error'
            } else {
              this.weatherMessage = `It is ${data.current.temp_c} degrees in ${data.location.name} right now with ${data.current.condition.text.toLowerCase()} conditions.`;
              this.icon = data.current.condition.icon;
              this.lastUpdated = "Updated on: " + data.current.last_updated;
              this.loadingStatus = 'success'
            }
          }).catch(() => {
            this.loadingStatus = 'error'
          })
        }, async () => {

          await fetch(import.meta.env.VITE_URL_API + '/v1/weather/current?' + new URLSearchParams({
            location: await this.getIpAdress(),
          })).then(response => response.json()).then(data => {
            this.weatherMessage = `It is ${data.current.temp_c} degrees in ${data.location.name} right now with ${data.current.condition.text.toLowerCase()}.
        The wind is currently blowing at ${data.current.wind_kph} km/h in a ${data.current.wind_dir} direction.`;
            this.icon = data.current.condition.icon;
            this.lastUpdated = "Updated on: " + data.current.last_updated;
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

</style>