# multiroom-chat-goof
A purposely vulnerable, multiroom chat application with Spring Boot on the backend and Vue, Vuex, Nuxt.js on the frontend. It uses websockets for near realtime communication between the server and clients.

## Overview

This project is based off the excellent work of [kojotdev](http://kojotdev.com). Read more about it 
[here](http://kojotdev.com/2019/09/multiroom-chat-with-spring-websocket-nuxt-vue-vuex/).

The project is organized in such a way that it can easily be run locally and be deployed for demonstration.

**NOTE:** The project has known vulnerabilities in it for demonstration purposes. PLEASE do not attempt to use in any production environment.

![multiroom-chatspring-vue-websocket-live-example](http://kojotdev.com/wp-content/uploads/2019/09/multiroom-chat-live.gif)

## Requirements

This app currently works with:

* node.js versions <= 16
* java versions <= 21

## Run - local, separated

Launch the backend with:

```
mvn clean install
CORS_ALLOWED_DOMAINS=http://localhost:3000 mvn spring-boot:run
```

The backend will launch on port `8080`

Launch the frontend with:

```
cd src/frontend
npm install
npm run dev
```

The frontend will launch on port `3000`

You can then browse to: `http://localhost:3000`

Any changes you make to the frontend will trigger an automatic re-build of the frontend.

Jump to the [Demo](#Demo) section.

## Run - local, unified

When you run `mvn clean install`, in addition to building the Java code, it performs the following for the frontend
app:

* builds the nuxt app
* generates a static version of the nuxt app
* copies the static frontend into `src/main/resources/public`

The last step is what enables the frontend app to be served by the spring boot app.

You can run the spring boot app as before:

```
mvn spring-boot:run
```

Now, you can access the frontend at: `http://localhost:8080` and it will automatically connect with backend.

Jump to the [Demo](#Demo) section.

## Run - remote, unified

The combination of the `Procfile` and `system.properties` files makes the app easily deployable to
[Heroku](https://www.heroku.com/).

**NOTE**: In order for the frontend to properly connect to the backend, you'll need to set an environment variable
called: `BASE_URL`.

Do the following to deploy to heroku using the cli too:

```
heroku apps:create my-great-app
heroku config:set BASE_URL=https://my-great-app.herokuapp.com
git push heroku main
```

This will push the code to heroku and run the unified build process. Once complete, you can access the app at the name
you gave it (ex: https://my-great-app.herokuapp.com)

## Demo

### Back Story

Here's the story so far:

We created this basic chat app. 

We did some basic security checking to make sure that we avoid common security vulnerabilities like, XSS.

### Try a basic XSS exploit

Demo a basic XSS attack by sending the following to chat:

```
<script>alert(1)</script>
```

_The sanitizer ensures that this is just passed through as plaintext._

The chat app worked, but it was kind of boring. So, we decided to add support for [markdown](https://www.markdownguide.org/cheat-sheet/) capabilities. 

Markdown allowed us to send over rich text as well as images and links.

Demo a basic markdown link:

```
[snyk](https://snyk.io)
```

But, perhaps we introduced a new vector of attack?

### Try a markdown XSS exploit 

Demo a markdown XSS attempt:

```
[snyky](javascript:alert(1))
```

_This does **not** work, but it does give us a strange artifiact - a single closing paren. Maybe we're on to something?_

### Try a clever markdown XSS exploit

```
[snyky](javascript&#58;alert(1&#41;)
```

_Explain what an HTML entity is. We're trying to bypass the sanitizer. This does not work, but it **is** different from the last attempt in that we don't see any chat at all!_

### Spoiler: This is going to work!

```
[snyky](javascript&#58this;alert(1&#41;)
```

_This works because of the browser being too permissive and because of a security vulnerability in the `marked` library._

Point out that this is just a "foot in the door" exploit and attackers would next create a more sophisticated payload.

Run the snyk scan and you'll see the 5 issues related to the `marked` library. The remediation advice is to upgrade to version `4.0.10`.

Quit the running frontend with `CTRL+c`.

Update the `package.json` to switch from `0.3.5` to `4.0.10` for the `marked` library.

Run `npm install` to install the new version of the `marked` library

**NOTE**: You will also need to make a minor code update to the file: `src/frontend/components/room/ChatContent.vue`

Replace the code at line 38:

```
import marked from 'marked';
```

with

```
import {marked} from 'marked';
```

This is necessary to accomodate a syntax change in this newer version of `marked`.

Restart the frontend as before:

```
npm run dev
```

### XSS Vuln Fixed!

Return to the front door of the app in your browser: [http://localhost:3000](http://localhost:3000)

Attempt the markdown XSS attack that previously worked:

```
[snyky](javascript&#58this;alert(1&#41;)
```

Note that now it no longer works.