<template>
  <WeatherAlertComponent/>
    <ShortWeatherMessage/>
  <WeatherBoxToday/>
    <SevenDayForecast/>
  <RainGraphComponent/>
</template>

<script lang="ts">
import ShortWeatherMessage from "../components/ShortWeatherMessage.vue";
import SevenDayForecast from "../components/SevenDayForecast.vue";
import WeatherBoxToday from "../components/WeatherBoxToday.vue";
import RainGraphComponent from "../components/RainGraphComponent.vue";
import WeatherAlertComponent from "../components/WeatherAlertComponent.vue";
export default {
  name: "Home",
  components: {WeatherAlertComponent, RainGraphComponent, WeatherBoxToday, SevenDayForecast, ShortWeatherMessage}
}
</script>
