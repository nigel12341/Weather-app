<template>
  <h1>Welcome!</h1>
  <img :src="icon" alt="Weather Icon"/>
<h2>{{weatherMessage}}</h2>

  <p>{{lastUpdated}}</p>
</template>

<script>
export default {
  name: "ShortWeatherMessage",
  data() {
    return {
      weatherMessage: '',
      ip: '',
      icon: '',
      lastUpdated: '',
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
          await fetch('https://api.weatherapi.com/v1/current.json?' + new URLSearchParams({
            key: import.meta.env.VITE_WEATHER_API_KEY,
            q: position.coords.latitude + ',' + position.coords.longitude
          })).then(response => response.json()).then(data => {
            this.weatherMessage = `It is ${data.current.temp_c} degrees in ${data.location.name} right now with ${data.current.condition.text.toLowerCase()}.
        The wind is currently blowing at ${data.current.wind_kph} km/h in a ${data.current.wind_dir} direction.`;
            this.icon = data.current.condition.icon;
            this.lastUpdated = "Updated on: " + data.current.last_updated;
          })
        }, async () => {

          await fetch('https://api.weatherapi.com/v1/current.json?' + new URLSearchParams({
            key: import.meta.env.VITE_WEATHER_API_KEY,
            q: await this.getIpAdress(),
          })).then(response => response.json()).then(data => {
            this.weatherMessage = `It is ${data.current.temp_c} degrees in ${data.location.name} right now with ${data.current.condition.text.toLowerCase()}.
        The wind is currently blowing at ${data.current.wind_kph} km/h in a ${data.current.wind_dir} direction.`;
            this.icon = data.current.condition.icon;
            this.lastUpdated = "Updated on: " + data.current.last_updated;
          })
        });
      }
    },
  }
}
</script>

<style scoped>

</style>