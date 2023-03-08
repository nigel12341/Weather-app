<template>
    <ShortWeatherMessage/>
    <SevenDayForecast/>
</template>

<script lang="ts">
import ShortWeatherMessage from "../components/ShortWeatherMessage.vue";
import SevenDayForecast from "../components/SevenDayForecast.vue";
export default {
  name: "Home",
  components: {SevenDayForecast, ShortWeatherMessage}
}
</script>
