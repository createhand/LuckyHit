<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE HTML>
<html>
<head>
<script type="text/javascript">
var UserAgent = navigator.userAgent;
if (UserAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null || UserAgent.match(/LG|SAMSUNG|Samsung/) != null)
{
  	location.href = "<%=request.getContextPath()%>/gameDetailList.do";
}
else
{
	location.href = "<%=request.getContextPath()%>/gameDetailList.do";
}
</script>
</head>
</html>