/**
 * Returns a message when the base route '/' is called
 */
const express = require('express');
const router = express.Router();
const AppConfig = require('../app.config');
const appConfig = new AppConfig();

/* GET base route. */
router.get('/', function(req, res, next) {
  res.json({
    apiRoot: `GET / - Events API Server. Version: ${appConfig.version}`,
    getEvents: `GET /api/events - returns the list of events`,
    addEvent: `POST /api/event - adds a new event supply the event object in JSON form in the body`,
    getEventById: `GET /api/event/:id - gets the event with the id`,
    updateEventById: `PUT /api/event/:id - updates the event with the id, supply the event object in JSON form in the body`,
    incrementLikes: `PUT /api/event/:id/like - updates the likes counter for the event with the id`,
    incrementDislikes: `PUT /api/event/:id/dislike - updates the dislikes counter for the event with the id`,
    deleteEvent: `DELETE /api/event/:id - deletes the event with the id`
});
});

router.get('/favicon.ico', (req, res) => res.status(204));

module.exports = router;
