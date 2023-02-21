<template>
  <div class="app-container">
    <telephone-inquire :urlType="param"/>
  </div>
</template>

<script>

import telephoneInquire from "@/components/telephoneInquire";
export default {
  name: "index",
  components:{telephoneInquire},
  // props:["type"],
  data() {
    return{
      param:''
    }
  },
  created() {
    let arr = this.$route.fullPath.split('/');
    this.param = arr[2];
  }
};
</script>
