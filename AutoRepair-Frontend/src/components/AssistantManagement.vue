<template>
  <div id="hello">
    <section
      class="ftco-appointment ftco-no-pt ftco-no-pb img;"
      style="background-image: url(../static/images/mechanicHand.jpg)"
    >
      <div class="overlay"></div>
      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-10 col-lg-6 half p-4 py-7 pl-lg-8">
            <h2 class="mb-4">Add Operating Hours</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-10">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select
                          name=""
                          id=""
                          v-model="dayOfWeek"
                          class="form-control"
                        >
                          <option value="">Select Day</option>
                          <!-- fix later -->
                          <option value="Monday">Monday</option>
                          <option value="Tuesday">Tuesday</option>
                          <option value="Wednesday">Wednesday</option>
                          <option value="Thursday">Thursday</option>
                          <option value="Friday">Friday</option>
                          <option value="Saturday">Saturday</option>
                          <option value="Sunday">Sunday</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="col-md-5">
                  <div class="form-group">
                    <div class="input-wrap">
                      <h5>Start Time</h5>
                      <input
                        type="time"
                        class="form-control appointment_time"
                        v-model="startTime"
                        placeholder="HH:mm"
                      />
                    </div>
                  </div>
                </div>

                <div class="col-md-5">
                  <div class="form-group">
                    <div class="input-wrap">
                      <h5>End Time</h5>

                      <input
                        type="time"
                        class="form-control appointment_time"
                        v-model="endTime"
                        placeholder="HH:mm"
                      />
                    </div>
                  </div>
                </div>
              </div>

              <div class="col-md-12">
                <div class="form-group">
                  <h5
                    v-if="errorAddOpHours"
                    style="color: red; padding-top: 20px"
                  >
                    Error: {{ errorAddOpHours }}
                  </h5>
                </div>
              </div>

              <div class="col-md-10">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
                    v-bind:disabled="!dayOfWeek || !startTime || !endTime"
                    @click="addOpHours(dayOfWeek, startTime, endTime)"
                  >
                    Add Operating Hours
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>

      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-10 col-lg-6 half p-4 py-7 pl-lg-8">
            <h2 class="mb-4">Edit Operating Hours</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-10">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select
                          name="new"
                          id=""
                          v-model="dayOfWeek1"
                          class="form-control"
                        >
                          <option value="">Operating Hour to Edit</option>
                          <option value="Monday">Monday</option>
                          <option value="Tuesday">Tuesday</option>
                          <option value="Wednesday">Wednesday</option>
                          <option value="Thursday">Thursday</option>
                          <option value="Friday">Friday</option>
                          <option value="Saturday">Saturday</option>
                          <option value="Sunday">Sunday</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="col-md-5">
                  <div class="form-group">
                    <div class="input-wrap">
                      <h5>New Start Time</h5>

                      <input
                        type="time"
                        class="form-control appointment_time"
                        v-model="startTime1"
                        placeholder="HH:mm"
                      />
                    </div>
                  </div>
                </div>

                <div class="col-md-5">
                  <div class="form-group">
                    <div class="input-wrap">
                      <h5>New End Time</h5>
                      <input
                        type="time"
                        class="form-control appointment_time"
                        v-model="endTime1"
                        placeholder="HH:mm"
                      />
                    </div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <h5
                      v-if="errorEditOpHours"
                      style="color: red; padding-top: 20px"
                    >
                      Error: {{ errorEditOpHours }}
                    </h5>
                  </div>
                </div>

                <div class="col-md-10">
                  <div class="form-group">
                    <button
                      type="button"
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="!dayOfWeek1 || !startTime1 || !endTime1"
                      @click="editOpHours(dayOfWeek1, startTime1, endTime1)"
                    >
                      Edit Operating Hours
                    </button>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>

      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-10 col-lg-6 half p-4 py-7 pl-lg-8">
            <h2 class="mb-4">Delete Operating Hours</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-10">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select
                          name=""
                          id=""
                          v-model="dayOfWeek2"
                          class="form-control"
                        >
                          <option value="">Select Day</option>
                          <option value="Monday">Monday</option>
                          <option value="Tuesday">Tuesday</option>
                          <option value="Wednesday">Wednesday</option>
                          <option value="Thursday">Thursday</option>
                          <option value="Friday">Friday</option>
                          <option value="Saturday">Saturday</option>
                          <option value="Sunday">Sunday</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div class="col-md-12">
                <div class="form-group">
                  <h5
                    v-if="errorDeleteOpHours"
                    style="color: red; padding-top: 20px"
                  >
                    Error: {{ errorDeleteOpHours }}
                  </h5>
                </div>
              </div>

              <div class="col-md-10">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-3 px-4"
                    v-bind:disabled="!dayOfWeek2"
                    @click="deleteOpHours(dayOfWeek2)"
                  >
                    Delete Operating Hours
                  </button>
                </div>
              </div>
            </form>
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
      class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img"
      style="background-image: url(../static/images/work-2.jpg)"
    >
      <div class="overlay"></div>

      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
            <h2 class="mb-4">Update Service</h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                        <select name="" id="" class="form-control" v-model="serviceName7">
                            <option disabled value="">Select Service</option>
                            <option v-for="service in services" :key=service.serviceName>
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
                        type="number"
                        min="0"
                        class="form-control"
                        v-model="duration7"
                        placeholder="New duration in minutes"
                      >
                    </div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <div class="input-wrap">
                      <input
                        type="number"
                        min="0"
                        step="0.01"
                        class="form-control"
                        v-model="price7"
                        placeholder="New price"
                      >
                    </div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                  <button 
                      type="button" 
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="!serviceName7||!duration7||!price7"
                      @click="updateservice(serviceName7, duration7, price7)">Update the Service</button>
                  </div>
                </div>

                  <div class="col-md-12">
									<div class="form-group">
                    <h5 v-if="errorUpdateService2" style="color:red; padding-top:20px">Error: {{errorUpdateService2}}</h5> 
                  </div>
								  </div>

              </div>
            </form>
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
      class="ftco-appointment ftco-section ftco-no-pt ftco-no-pb img"
      style="background-image: url(../static/images/bg_3.jpg)"
    >
      <div class="overlay"></div>
      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
            <h2 class="mb-4">Add reminder </h2>
            <form action="#" class="appointment">
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div class="form-field">
                      <div class="select-wrap">
                        <div class="icon">
                          <span class="fa fa-chevron-down"></span>
                        </div>
                         <select name="" id="" class="form-control" v-model="username5">
                            <option disabled value="">Select Customer</option>
                            <option v-for="customer in customers"
                             
                             :key=customer.username>
                                {{ customer.username }}
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
                          <select name="" id="" class="form-control" v-model="serviceName5">
                            <option disabled value="">Select Service</option>
                            <option v-for="service in services"
                             
                             :key=service.serviceName>
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
                        type="Date"
                        class="form-control appointment_date"
                        v-model="datestring"
                        placeholder="Date"
                      >
                    </div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <div class="input-wrap">
                      <input
                        type="time"
                        class="form-control"
                        v-model="timestring"
                        placeholder="New duration in minutes"
                      >
                    </div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <textarea
                      name=""
                      id=""
                      cols="30"
                      rows="7"
                      class="form-control"
                      v-model="description"
                      placeholder="Description"
                    ></textarea>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">

                    <button 
                      type="button" 
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="!username5 || !serviceName5 || !datestring || !description || !timestring"
                      v-on:click="addreminder(username5, serviceName5, datestring , description, timestring)">Add the reminder</button>
                  </div>
                </div>

                  <div class="col-md-12">
									<div class="form-group">
                    <h5 v-if="errorCreateReminder" style="color:red; padding-top:20px">Error: {{errorCreateReminder}}</h5> 
                  </div>
								  </div>

              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->

      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
           
            <h2 class="mb-4">Update reminder</h2>
            <form action="#" class="appointment">
              <div class="row">

              <div class="col-md-12">
                <div class="form-group">
                  <div class="form-field">
                    <div class="select-wrap">
                      <div class="icon">
                        <span class="fa fa-chevron-down"></span>
                      </div>
                      <select
                        name="selectedReminder"
                        id=""
                        class="form-control"
                        v-model="selected2"
                      >
                        <option value="">Select Reminder</option>
                        <option
                          v-for="reminder in reminders"
                          v-bind:value="{ username6: reminder.customer.username,
                          oldServiceName: reminder.chosenService.name
                          }"
                          :key="reminder.id" 
                        >
                           {{ reminder.customer.username}}; {{ reminder.chosenService.name}}
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
                          <select name="" id="" class="form-control" v-model="newServiceName">
                            <option disabled value="">Select New Service</option>
                            <option v-for="service in services"
                             
                             :key=service.serviceName>
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
                        type="Date"
                        class="form-control appointment_date"
                        v-model="datestring2"
                        placeholder="Date"
                      >
                    </div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <div class="input-wrap">
                      <input
                        type="time"
                        class="form-control"
                        v-model="timestring2"
                        placeholder="New duration in minutes"
                      >
                    </div>
                  </div>
                </div>

                <div class="col-md-12">
                  <div class="form-group">
                    <textarea
                      name=""
                      id=""
                      cols="30"
                      rows="7"
                      class="form-control"
                      v-model="description2"
                      placeholder="Description"
                    ></textarea>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <button 
                      type="button" 
                      class="btn btn-dark py-3 px-4"
                      v-bind:disabled="!selected2 || !newServiceName || !datestring2 || !description2 || !timestring2"
                      @click="updatereminder(selected2.oldServiceName, newServiceName, selected2.username6, datestring2, description2, timestring2)">Update the reminder</button>
                  </div>
                </div>

                  <div class="col-md-12">
									<div class="form-group">
                    <h5 v-if="errorUpdateReminder" style="color:red; padding-top:20px">Error: {{errorUpdateReminder}}</h5> 
                  </div>
								  </div>

              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->

      <div class="container">
        <div class="row d-md-flex justify-content-end">
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
           
            <h2 class="mb-4">Delete Reminder</h2>
            <form action="#" class="appointment">
              <div class="row">
                
              <div class="col-md-12">
                <div class="form-group">
                  <div class="form-field">
                    <div class="select-wrap">
                      <div class="icon">
                        <span class="fa fa-chevron-down"></span>
                      </div>
                      <select
                        name="selectedReminder"
                        id=""
                        class="form-control"
                        v-model="selected"
                      >
                        <option value="">Select Reminder</option>
                        <option
                          v-for="reminder in reminders"
                          v-bind:value="{ username4: reminder.customer.username,
                          serviceName4: reminder.chosenService.name
                          }"
                          :key="reminder.id" 
                        >
                           {{ reminder.customer.username}}; {{ reminder.chosenService.name}}
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
                      v-bind:disabled="!selected"
                      v-on:click="deletereminder(selected.username4, selected.serviceName4)">delete the reminder</button>
                    </div>
                  </div> 

                  <div class="col-md-12">
									<div class="form-group">
                    <h5 v-if="errorDeleteReminder" style="color:red; padding-top:20px">Error: {{errorDeleteReminder}}</h5> 
                  </div>
								  </div>

              </div>
            </form>
          </div>
        </div>
      </div>
    </section>
   
   
    <div style="background-color: #262626">
      <br />
      <br />
      <br />
    </div>
    <br>
    <br>

    <div class="centered">
      <div>
        <h2 style="text-align: center">Reminders</h2>
        <table
          style="
            width: 80%;
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
              <th style="border: 3px solid orange">Customer</th>
              <th style="border: 3px solid orange">Service</th>
              <th style="border: 3px solid orange">Date</th>
              <th style="border: 3px solid orange">Time</th>
              <th style="border: 3px solid orange">Description</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="reminder in reminders" :key=reminder.id
              style="background-color: rgba(255, 165, 0, 0.5)"
            >
              <td
                style="
                  border: 1px solid orange;
                  background-color: rgba(255, 165, 0, 0.2);
                "
              >
                {{ reminder.customer.username}}
              </td>
              <td
                style="
                  border: 1px solid orange;
                  background-color: rgba(255, 165, 0, 0.2);
                "
              >
                {{ reminder.chosenService.name}}
              </td>
              <td
                style="
                  border: 1px solid orange;
                  background-color: rgba(255, 165, 0, 0.2);
                "
              >
                {{ reminder.date}}
              </td>
              <td
                style="
                  border: 1px solid orange;
                  background-color: rgba(255, 165, 0, 0.2);
                "
              >
                {{ reminder.time}}
              </td>
              <td
                style="
                  border: 1px solid orange;
                  background-color: rgba(255, 165, 0, 0.2);
                "
              >
                {{ reminder.description}}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <br>
    <br>
    <br>
  </div>
</template>

<script src="./js/AssistantManagement.js"></script>
