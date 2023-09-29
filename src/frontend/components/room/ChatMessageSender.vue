<template>
  <v-flex
    sm12
    md8
    text-center
  >
    <form @submit.prevent="sendMessage">
      <v-text-field
        :counter="maxMessageLength"
        :rules="rules"
        label="Type a message"
        type="text"
        v-model="message"
      >
        <template v-slot:append>
          <v-btn
            color="primary"
            @click="sendMessage"
          >
            Send message
            <v-icon right>keyboard_return</v-icon>
          </v-btn>
        </template>
      </v-text-field>
    </form>
  </v-flex>
</template>

<script>
export default {
  data() {
    return {
      message: "",
      maxMessageLength: 500,
      rules: [v => v.length <= this.maxMessageLength || 'Max 500 characters']
    };
  },
  computed: {
    roomId() {
      return this.$route.params.roomId;
    }
  },
  methods: {
    sendMessage() {
      if (this.message.length > this.maxMessageLength) {
        return;
      };
      const newMessage = {
        type: "MESSAGE",
        message: this.message
      };
      const messageWithRoomId = { roomId: this.roomId, message: newMessage };
      this.$store.dispatch("main/sendMessage", messageWithRoomId);
      this.message = "";
    }
  }
};
</script>

<style scoped>
</style>
