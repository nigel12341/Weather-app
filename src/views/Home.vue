<template>
    <ShortWeatherMessage/>
  <WeatherBoxToday/>
    <SevenDayForecast/>
</template>

<script lang="ts">
import ShortWeatherMessage from "../components/ShortWeatherMessage.vue";
import SevenDayForecast from "../components/SevenDayForecast.vue";
import WeatherBoxToday from "../components/WeatherBoxToday.vue";
export default {
  name: "Home",
  components: {WeatherBoxToday, SevenDayForecast, ShortWeatherMessage}
}
</script>
