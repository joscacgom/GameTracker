<template>
    <footer class="footer">
      <div class="footer-content">
        <p>&copy; 2023 GameTracker. All rights reserved.</p>
        <ul class="footer-links">
          <li><a href="#">Terms of Service</a></li>
          <li><a href="#">Privacy Policy</a></li>
          <li><a href="#">Contact Us</a></li>
        </ul>
      </div>
    </footer>
  </template>
  
  <style scoped>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');
  .footer {
    background-color: rgb(252, 9, 76);
    padding: 20px;
    text-align: center;
  }
  
  .footer-content {
    max-width: 800px;
    margin: 0 auto;
  }
  
  .footer p {
    font-family: Poppins;
    color: #ffffff;
    margin-bottom: 10px;
  }
  
  .footer-links {
    font-family: Poppins;
    list-style: none;
    padding: 0;
    margin-top: 10px;
  }
  
  .footer-links li {
    display: inline-block;
    margin-right: 10px;
  }
  
  .footer-links li:last-child {
    margin-right: 0;
  }
  
  .footer-links a {
    color: #ffffff;
    text-decoration: none;
  }
  
  .footer-links a:hover {
    text-decoration: underline;
  }
  </style>
  