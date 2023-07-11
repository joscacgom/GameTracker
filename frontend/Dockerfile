FROM node:16.13.1

# Copy the project files to the container
COPY . /app

# Set the working directory
WORKDIR /app

# Install the dependencies
RUN npm install

# Set the environment variable
ENV VUE_APP_BACKEND_URL="http://localhost:8080"

# Expose port 8081
EXPOSE 8081

# Start the Vue.js application
CMD ["npm", "run", "serve", "--", "--port", "8081"]
