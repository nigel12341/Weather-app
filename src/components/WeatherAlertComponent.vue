<template>
  <div v-if="loadingStatus === 'success'" class="container">
    <div v-for="alert in weatherMessage" class="alert alert-danger" role="alert">
      <h1 class="alert-heading">
        <svg xmlns="http://www.w3.org/2000/svg" width="38" height="38" fill="currentColor"
             class="bi bi-exclamation-triangle-fill" viewBox="0 0 16 16">
          <path
              d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
        </svg>
        Weather Alert
      </h1>
      <p>Issued by: {{ alert.headline }}</p>
      <h4>{{ alert.event }}</h4>
      <p>{{ alert.desc }}</p>
      <p>Effective since: {{ convertISODate(alert.effective) }}</p>
      <p>Expires at: {{ convertISODate(alert.expires) }}</p>
    </div>
  </div>
</template>

<script lang="ts">
import moment from "moment";

export default {
  name: "WeatherAlertComponent",
  data() {
    return {
      weatherMessage: [] as any,
      ip: '' as any,
      loadingStatus: 'loading',
    }
  },
  mounted() {
    this.getWeatherMessage();
  },
  methods: {
    async getIpAdress() {
      let response = await fetch('https://api.ipify.org?format=json');
      let data = await response.json();
      return data.ip
    },

    async getWeatherMessage() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(async (position) => {
          await fetch(import.meta.env.VITE_URL_API + '/v1/weather/forecast?' + new URLSearchParams({
            location: position.coords.latitude + ',' + position.coords.longitude
          })).then(response => response.json()).then(data => {
            if (data.status) {
              this.loadingStatus = 'error'
            } else {
              if (data.alerts.alert) {
                this.weatherMessage = data.alerts.alert;
                this.location = data.location
                this.loadingStatus = 'success'
              } else {
                this.loadingStatus = 'error'
              }

            }
          }).catch(() => {
            this.loadingStatus = 'error'
          })
        }, async () => {

          await fetch(import.meta.env.VITE_URL_API + '/v1/weather/forecast?' + new URLSearchParams({
            location: await this.getIpAdress(),
          })).then(response => response.json()).then(data => {
            if (data.status) {
              this.loadingStatus = 'error'
            } else {
              if (data.alerts.alert) {
                this.weatherMessage = data.alerts.alert;
                this.location = data.location
                this.loadingStatus = 'success'
              } else {
                this.loadingStatus = 'error'
              }
            }
          }).catch(() => {
            this.loadingStatus = 'error'
          })
        });
      }
    },

    convertISODate(date: string) {
      let momentDate = moment(date);
      let dateComponent = momentDate.format('YYYY-MM-DD');
      let timeComponent = momentDate.format('HH:mm');
      let jsDate = dateComponent + " " + timeComponent;

// Now, you can run any JavaScript Date method
      return jsDate;
    }
  }

}
</script>

<style scoped>
.container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  width: 100%;
  align-items: center;
  justify-content: center;
}
</style>