<template>
  <div class="all">
    <look/>
  </div>
</template>

<style>
  .all{
    display: flex;
  }
</style>

<script>
import Aside from "@/components/telephoneInquire/Aside";
import look from "@/components/telephoneInquire/look";
export default {
  name: "index",
  components:{Aside,look}
};
</script>
