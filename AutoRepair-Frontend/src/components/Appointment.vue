  <template>
  <div id="appointment">
    
    <section
      class="ftco-section"
      style="
        background-image: url(../static/images/bg_3.jpg);
        height: 667px;
        background-size: cover;
      "
    >
      <div class="overlay"></div>
      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
            <div
              class="form-group"
              style="
                background-color: rgba(255, 165, 0, 0.7);
                height: 330px;
                width: 600px;
                text-align: center;
                border-radius: 25px;
                padding: 20px;
              "
            >
              <h2>Book An Appointment!</h2>
              <form action="#/appointments" class="appointment">
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <div class="form-field">
                        <div class="select-wrap">
                          <div class="icon">
                            <span class="fa fa-chevron-down"></span>
                          </div>
                          <select
                            name="serviceName"
                            id=""
                            class="form-control"
                            v-model="serviceName"
                          >
                            <option value="">Select Service</option>
                            <option v-for="service in services" :key="service.name">
                              {{ service.name }}
                            </option>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap">
                        <input
                          type="date"
                          class="form-control appointment_date"
                          placeholder="Date"
                          v-model="appointmentDate"
                          name="appointmentDate"
                          @change="
                            getAvailableTimeSlots(appointmentDate.toString())
                          "
                        />
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap">
                        <input
                          type="time"
                          class="form-control appointment_time"
                          placeholder="Time"
                          v-model="appointmentTime"
                          name="appointmentTime"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <button
                        type="button"
                        class="btn btn-dark py-3 px-4"
                        @click="
                          makeAppointment(
                            tamara,
                            appointmentDate.toString(),
                            appointmentTime.toString(),
                            serviceName
                          ),
                            getAppointmentsOfCustomer()
                        "
                      >
                        Book
                      </button>
                    </div>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>

    <div style="background-color: #262626">
      <br />
      <br />
      <br />
    </div>

    <section
      class="ftco-section"
      style="
        background-image: url(../static/images/work-2.jpg);
        background-size: cover;
      "
    >
      <div class="overlay"></div>
      <div class="container">
        <div
          class="form-group"
          style="
            background-color: rgba(255, 165, 0, 0.7);
            height: 420px;
            width: 600px;
            text-align: center;
            border-radius: 25px;
            padding: 20px;
            margin: center;
          "
        >
          <h2 class="mb-4">Update Your Appointment!</h2>
          <form action="#/appointments" class="appointment">
            <div class="row">
              <div class="col-md-12">
                <div class="form-group">
                  <div class="form-field">
                    <div class="select-wrap">
                      <div class="icon">
                        <span class="fa fa-chevron-down"></span>
                      </div>
                      <select
                        name="selectedAppointment"
                        id=""
                        class="form-control"
                        v-model="selectedUpdate"
                      >
                        <option value="">Select An Appointment</option>
                        <option v-for="appointment in appointments"
                          v-bind:value="{ serviceName: appointment.service.name,
                          appointmentDate: appointment.timeSlot.startDate,
                          appointmentTime: appointment.timeSlot.startTime}"
                          :key=appointment.id>
                          {{ appointment.service.name}}; {{ appointment.timeSlot.startDate}}; {{ appointment.timeSlot.startTime}}
                        </option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <div class="form-field">
                    <div class="select-wrap">
                      <div class="icon">
                        <span class="fa fa-chevron-down"></span>
                      </div>
                      <select
                        name="newServiceName"
                        id=""
                        class="form-control"
                        v-model="newServiceName"
                      >
                        <option value="">Select A New Service</option>
                        <option v-for="service in services" :key=service.name>
                          {{ service.name }}
                        </option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-group">
                  <div class="input-wrap">
                    <input
                      type="date"
                      class="form-control appointment_date"
                      placeholder="Date"
                      v-model="newAppointmentDate"
                      name="newAppointmentDate"
                      @change="
                        getAvailableTimeSlots(newAppointmentDate.toString())
                      "
                    />
                  </div>
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-group">
                  <div class="input-wrap">
                    <input
                      type="time"
                      class="form-control appointment_time"
                      placeholder="Time"
                      v-model="newAppointmentTime"
                      name="newAppointmentTime"
                    />
                  </div>
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
                    @click="
                      updateAppointment(
                        selectedUpdate.serviceName,
                        selectedUpdate.appointmentDate,
                        selectedUpdate.appointmentTime,
                        newAppointmentTime.toString(),
                        newAppointmentDate.toString(),
                        newServiceName
                      )
                    "
                  >
                    Update
                  </button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </section>
    <div style="background-color: #262626">
      <br />
      <br />
      <br />
    </div>
    <section
      class="ftco-section"
      style="
        background-image: url(../static/images/work-1.jpg);
        height: 667px;
        background-size: cover;
      "
    >
      <div class="overlay"></div>
      <div class="container">
        <div
          class="form-group"
          style="
            background-color: rgba(255, 165, 0, 0.7);
            height: 260px;
            width: 600px;
            text-align: center;
            border-radius: 25px;
            padding: 20px;
          "
        >
          <h2>Cancel Your Appointment!</h2>
          <form action="#/appointments" class="appointment">
            <div class="row">
              <div class="col-md-12">
                <div class="form-group">
                  <div class="form-field">
                    <div class="select-wrap">
                      <div class="icon">
                        <span class="fa fa-chevron-down"></span>
                      </div>
                      <select
                        name="selectedAppointment"
                        id=""
                        class="form-control"
                        v-model="selectedCancel"
                      >
                        <option value="">Select An Appointment</option>
                        <option
                          v-for="appointment in appointments"
                          v-bind:value="{ serviceName: appointment.service.name,
                          appointmentDate: appointment.timeSlot.startDate,
                          appointmentTime: appointment.timeSlot.startTime}"
                          :key="appointment.id"
                        >
                           {{ appointment.service.name}}; {{ appointment.timeSlot.startDate}}; {{ appointment.timeSlot.startTime}}
                        </option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
                    @click="cancelAppointment(selectedCancel.serviceName, selectedCancel.appointmentDate, selectedCancel.appointmentTime)"
                  >
                    Cancel
                  </button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </section>
    <br />

    <div class="split left">
      <div class="centered">
        <div>
          <h2 style="text-align: center">My Appointments</h2>
          <table
            style="
              width: 60%;
              margin-left: auto;
              margin-right: auto;
              border: 1px solid black;
              border-collapse: collapse;
              table-layout: fixed;
              text-align: center;
            "
          >
            <thead>
              <tr style="background-color: rgba(255, 165, 0, 0.8)">
                <th style="border: 3px solid orange">Service</th>
                <th style="border: 3px solid orange">Date</th>
                <th style="border: 3px solid orange">Time</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="appointment in appointments" :key=appointment.id
                style="background-color: rgba(255, 165, 0, 0.5)"
              >
                <td
                  style="
                    border: 1px solid orange;
                    background-color: rgba(255, 165, 0, 0.2);
                  "
                >
                  {{ appointment.service.name}}
                </td>
                <td
                  style="
                    border: 1px solid orange;
                    background-color: rgba(255, 165, 0, 0.2);
                  "
                >
                  {{ appointment.timeSlot.startDate}}
                </td>
                <td
                  style="
                    border: 1px solid orange;
                    background-color: rgba(255, 165, 0, 0.2);
                  "
                >
                  {{ appointment.timeSlot.startTime}} -
                  {{ appointment.timeSlot.endTime}}
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <br />
    <br />
    <br />

    <div class="split right">
      <div class="centered">
        <h2 style="text-align: center">Time Slots</h2>
        <table
          style="
            width: 30%;
            margin-left: auto;
            margin-right: auto;
            border: 3px solid green;
            border-collapse: collapse;
            text-align: center;
          "
        >
          <tr>
            <th
              style="
                border: 3px solid green;
                background-color: rgba(0, 128, 0, 0.4);
              "
            >
              Available Time Slots
            </th>
          </tr>
          <tr
            v-for="availableTimeSlot in availableTimeSlots"
            :key="availableTimeSlot.id"
            style="background-color: rgba(0, 128, 0, 0.2)"
          >
            <td style="border: 1px solid green">
              {{ availableTimeSlot.startTime }} -
              {{ availableTimeSlot.endTime }}
            </td>
          </tr>
        </table>
      </div>
    </div>
    <br />
    <br />
    <br />
  </div>
</template>

<script src="./js/Appointment.js"></script>


