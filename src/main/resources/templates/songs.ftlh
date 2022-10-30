<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
</head>
<body>
    <h1>Hello !</h1>
    <p>Welcome to the <span>${name_app}</span></p>
    <h2>List of songs</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Published</th>
            <th>Language</th>
        </tr>
        <#if song??>
        <tr>
            <td>${song.id}</td>
            <td>${song.title}</td>
            <td>${song.author}</td>
            <td>${song.year_pub?date}</td>
            <td>${song.language}</td>
        </tr>
        <#else>
            <#list songs as song>
            <tr>
                <td>${song.id}</td>
                <td>${song.title}</td>
                <td>${song.author}</td>
                <td>${song.year_pub?date}</td>
                <td>${song.language}</td>
            </tr>
        </#list>
        </#if>
    </table>
</body>
</html>