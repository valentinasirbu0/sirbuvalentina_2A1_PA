<!DOCTYPE html>
<html lang="en">
<head>
  <style>
    table,th,td {
    border: 1px solid black;
    border-collapse: collapse;
    }
    th,td {
    padding: 5px;
    text-align: left;
    }
  </style>
</head>
<body>
<h2>${title}</h2>
<table>
  <tr>
    <th>ID</th>
    <th>Title</th>
    <th>Location</th>
    <th>Tags</th>
  </tr>
  <#list docs as d>
  <tr>
    <td>${d.id}</td>
     <#if d.title??> <td>${d.title}</td>
     <#else><td> <td></#if>
     <#if d.location??> <td>${d.location}</td>
     <#else><td> <td></#if>
     <td>${d.tags}</td>
  </tr>
</#list><!-- End of docs list -->
</table>
</body>
</html>