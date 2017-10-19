<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="./commons/_head.jspf" %>
<script src="//code.jquery.com/jquery.min.js"></script>
</head>
<body>

<c:out value="${product.name}"/> / <c:out value="${product.age}"/>

<script>
var person = {
	name : "hong",
	age :30,
	sex: "male"
};

var person_str = JSON.parse('${ppp}');
//nsole.log(person);
console.log(person_str);
//ori = ${ppp};
//ori = JSON.stringify(${ppp});
//var contactsLength = ori.length;
//console.log(contactsLength);
//ori2 = JSON.parse(ori)
//data1 = JSON.stringify(ori2);
//data2 = JSON.parse(data1);
//console.log(data1);
//console.log(data2);
//console.log(JSON.parse("${product}"));
/* JSON Definition */ 
 
 //var json = '{ "NAME" : "Yongwoo", "AGE" : 25, "UNIVERSITY" : "Catholic Univ, of Korea", "MAJOR" : "Computer Science" }'; 
 /* JSON Parse and Check */ 
 //var jsonObj = JSON.parse(json);
 
 //console.log(jsonObj);

function rplLine(value){
    if (value != null && value != "") { 
        return value.replace(/\n/g, "\\n");
    }else{
        return value;
    }
}

</script>
</body>
</html>