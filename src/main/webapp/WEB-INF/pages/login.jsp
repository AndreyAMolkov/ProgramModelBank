<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="<c:url value="/login"></c:url>" method="post" role="form">

    <fieldset>
        <legend>Please Login</legend>
        <!-- use param.error assuming FormLoginConfigurer#failureUrl contains the query parameter error -->
        <c:if test="${param.error != null}">
            <div>
                Failed to login.
                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                  Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                </c:if>
            </div>
        </c:if>
        <!-- the configured LogoutConfigurer#logoutSuccessUrl is /login?logout and contains the query param logout -->
        <c:if test="${param.logout != null}">
            <div>
                You have been logged out.
            </div>
        </c:if>
         <c:if test="${param.accessDenied != null}">
            <div>
                Access denied.
            </div>
        </c:if>
        <p>
        login:<br> 
        <input type="text" name="username"/>
        </p>
        <p>
        password:<br>
        <input type="password"  name="password"/>
        </p>
        <!-- if using RememberMeConfigurer make sure remember-me matches RememberMeConfigurer#rememberMeParameter -->
        <p>
       
        </p>
        <div>
            <button type="submit" class="btn">Log in</button>
        </div>
    </fieldset>
 </form>