<template>
  <div id="UpdateAppointment">
    <h2> Update Your Appointment! </h2>
    <label>Appointment to update:
      <select v-model="selectedAppointment">
           <option disabled value="">Please select one</option>
            <option v-for="appointment in appointments" >
              {{ appointment.toString() }}
            </option>
      </select>
    </label>
    <button type="button">Change the Service</button>
    <button type="button">Change Time Slot</button>
    <button type="button">Change both</button>


  </div>
</template>

<script src="./js/updateAppointment.js"></script>

<Style>
</style>
