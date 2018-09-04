<%@page import="javax.security.auth.kerberos.KerberosKey"%>
<%@page import="conn.DatabaseConnection"%>
<%@page import="entity.Property"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>SEARCH PAGE</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="css/table.css" type="text/css" />
<jsp:include page="SearchInput.jsp"></jsp:include>
<jsp:useBean id="Dbc" class="conn.DatabaseConnection"></jsp:useBean>
<jsp:useBean id="Dbc_property" class="conn.DatabaseConnection"></jsp:useBean>
<jsp:useBean id="Dbc_value" class="conn.DatabaseConnection"></jsp:useBean>
</head>

<body >
	<%
		request.setCharacterEncoding("UTF-8");
		Dbc.getConn();
		Dbc_property.getConn();
		Dbc_value.getConn();
		String searchString = request.getParameter("searchText");
		if(!searchString.equals("")){
			searchString=searchString.trim();	
		if(searchString.contains(" ")){
			boolean isLoop = true;
			String sections[] = searchString.split(" ");
			if(sections.length==2){
				if(Dbc.searchByString(sections[0])){
					for(int i=0;i<Dbc.matchingEntityIDs.size();i++){
						isLoop = true;
						if(Dbc.searchInProperty(Dbc.matchingEntityIDs.get(i))){
							int j = 0;
							while(j<Dbc.Property.size()){
								if(Dbc_property.searchInLabels(Dbc.Property.get(j).property_id)){
									for(int k = 0;k<Dbc_property.Labels.size();k++){
										if(Dbc_property.Labels.get(k).content.equals(sections[1])){
											if(Dbc.Property.get(j).valueType.equals("wikibase-item")|| Dbc.Property.get(j).valueType.equals("wikibase-property"))
											{
												if(Dbc_value.searchInLabels(Dbc.Property.get(j).value)){
													for(int n=0;n<Dbc_value.Labels.size() ;n++){
														if(Dbc_value.Labels.get(n).language.equals("en")){
															%>
															<div><%=Dbc_value.Labels.get(n).content %><br></div>
															<%
															
															break;
														}
													}
													for(int n=0;n< Dbc_value.Labels.size() ;n++){
														if(!Dbc_value.Labels.get(n).language.equals("en")){
															%>
															<div><%=Dbc_value.Labels.get(n).content %><br></div>
															<%
															break;
														}
													}
												}
												isLoop=false;
											}else{
												%>
												<div><%=Dbc.Property.get(j).value%><br></div>
												<%
												isLoop=false;
											}
										}
									}
								}
								///////////////////////
								if(isLoop==true){
								if(Dbc_property.searchInAliases(Dbc.Property.get(j).property_id)){
									for(int k = 0;k<Dbc_property.Aliases.size();k++){
										if(Dbc_property.Aliases.get(k).content.equals(sections[1])){
											if(Dbc.Property.get(j).valueType.equals("wikibase-item")|| Dbc.Property.get(j).valueType.equals("wikibase-property"))
											{
												if(Dbc_value.searchInLabels(Dbc.Property.get(j).value)){
													for(int n=0;n<Dbc_value.Labels.size() ;n++){
														if(Dbc_value.Labels.get(n).language.equals("en")){
															%>
															<div><%=Dbc_value.Labels.get(n).content %><br></div>
															<%
															
															break;
														}
													}
													for(int n=0;n< Dbc_value.Labels.size() ;n++){
														if(!Dbc_value.Labels.get(n).language.equals("en")){
															%>
															<div><%=Dbc_value.Labels.get(n).content %><br></div>
															<%
															break;
														}
													}
												}
												isLoop=false;
											}else{
												%>
												<div><%=Dbc.Property.get(j).value%><br></div>
												<%
												isLoop=false;
											}
										}
									}
								}
								}
								///////////////////////
								
								j++;
							}
							
						}
						if(isLoop==false){
							break;
						}
						
					}
					if(isLoop==true){
						%>
						<div ><br><br><br>Search Property Is Not Match!<br><br><br>未搜索到该属性关键词！</div>
						<% 
					}
				}
				else{
					%>
					<div ><br><br><br>Search String Is Not Match!<br><br><br>未搜索到该关键词！</div>
					<% 
				}
				
			}else{
				%>
				<div ><br><br><br>Search String Form Is Not Correct!<br><br><br>搜索关键字格式不对！</div>
				<% 
			}
		}
		else{
		boolean isGetEntityID = Dbc.searchByString(request.getParameter("searchText"));
	%>
	<%
		if (isGetEntityID) {
	%>
	<%
		for (int i = 0; i < Dbc.matchingEntityIDs.size(); i++) {
	%>
	<div align ="center">
	<table style="width: 1249px; ">
	<caption>Search for Phrase"<%=request.getParameter("searchText") %>"<%=Dbc.matchingEntityIDs.size()%>  branch for <%=i+1 %></caption>
		<tr>
			<td style="background-color: #C0C0C0" width="30%">Information(信息)<br> </td>
			<td style="background-color: #C0C0C0" width="35%">English</td>
			<td style="background-color: #C0C0C0" width="35%">中文</td>
		</tr>
		<tr>
			<td>Labels(标签):</td>
			<td>
				<%
					if (Dbc.searchInLabels(Dbc.matchingEntityIDs.get(i))) {
								for (int l = 0; l < Dbc.Labels.size(); l++) {
									if (Dbc.Labels.get(l).language.equals("en")) {
				%><%=Dbc.Labels.get(l).content + " "%> <%
 	}
 				}

 			}
 %>
			</td>
			<td>
				<%
					for (int l = 0; l < Dbc.Labels.size(); l++) {
								if (!Dbc.Labels.get(l).language.equals("en")) {
				%><%=Dbc.Labels.get(l).content + " "%> <%
 	}
 			}
 %>
			</td>
		</tr>
		<tr>
			<td>Aliases(别名):</td>
			<td>
				<%
					if (Dbc.searchInAliases(Dbc.matchingEntityIDs.get(i))) {
								for (int l = 0; l < Dbc.Aliases.size(); l++) {
									if (Dbc.Aliases.get(l).language.equals("en")) {
				%><%=Dbc.Aliases.get(l).content + " "%> <br> <%
 	}
 				}

 			}
 %>
			</td>
			<td>
				<%
					if (Dbc.searchInAliases(Dbc.matchingEntityIDs.get(i))) {
								for (int l = 0; l < Dbc.Aliases.size(); l++) {
									if (!Dbc.Aliases.get(l).language.equals("en")) {
				%><%=Dbc.Aliases.get(l).content + " "%><br> <%
 	}
 				}

 			}
 %>
			</td>
		</tr>
		<tr>
			<td>Descriptions(描述):</td>
			<td>
				<%
					if (Dbc.searchInDescriptions(Dbc.matchingEntityIDs.get(i))) {
								for (int l = 0; l < Dbc.Descriptions.size(); l++) {
									if (Dbc.Descriptions.get(l).language != null) {
										if (Dbc.Descriptions.get(l).language.equals("en")) {
				%><%=Dbc.Descriptions.get(l).content + " "%> <br> <%
 	}
 					}

 				}

 			}
 %>
			</td>
			<td>
				<%
					if (Dbc.searchInDescriptions(Dbc.matchingEntityIDs.get(i))) {
								for (int l = 0; l < Dbc.Descriptions.size(); l++) {
									if (Dbc.Descriptions.get(l).language != null) {
										if (!Dbc.Descriptions.get(l).language.equals("en")) {
				%><%=Dbc.Descriptions.get(l).content + " "%>
				<%
					}
									}

								}

							}
				%>
			</td>
		</tr>
<!-- 	</table>
	<table>
	<tr><td></td><td>English</td><td>中文</td></tr> -->
		<%
		int p=0;
		Dbc.searchInProperty(Dbc.matchingEntityIDs.get(i));
		while( p < Dbc.Property.size()) {
		   %>
		   <tr><td>
		   <%Dbc_property.searchInLabels(Dbc.Property.get(p).property_id); %>
		   <%
					for (int k = 0; k < Dbc_property.Labels.size(); k++) {
											if (Dbc_property.Labels.get(k).language.equals("en")) {
				%> <%=Dbc_property.Labels.get(k).content%>
				<%
					}
										}
					for (int k = 0; k < Dbc_property.Labels.size(); k++) {
											if (!Dbc_property.Labels.get(k).language.equals("en")) {
				%> <%="("+Dbc_property.Labels.get(k).content+")"%>
				<%
					}
										}
		   %>
		   </td>
		   <td>
		   <%
		   	if (Dbc.Property.get(p).valueType.equals("wikibase-item")|| Dbc.Property.get(p).valueType.equals("wikibase-property"))
		   	{
					Dbc_value.searchInLabels(Dbc.Property.get(p).value);%>
					<%
					for (int k = 0; k < Dbc_value.Labels.size(); k++) {
											if (Dbc_value.Labels.get(k).language.equals("en")) {
				%> <%=Dbc_value.Labels.get(k).content%><br>
				<%
					}
										}
				
				}else{%>
				<%=Dbc.Property.get(p).value%><br>
				<% 
				}
				//
				if(p+1<Dbc.Property.size()){
				int temp_p=p;
				while(temp_p+1<Dbc.Property.size()&&Dbc.Property.get(temp_p+1).property_id.equals(Dbc.Property.get(temp_p).property_id))
				{++temp_p;
				   		   	if (Dbc.Property.get(temp_p).valueType.equals("wikibase-item")|| Dbc.Property.get(temp_p).valueType.equals("wikibase-property"))
		   	{
					Dbc_value.searchInLabels(Dbc.Property.get(temp_p).value);%>
					<%
					for (int k = 0; k < Dbc_value.Labels.size(); k++) {
											if (Dbc_value.Labels.get(k).language.equals("en")) {
				%> <%=Dbc_value.Labels.get(k).content%><br>
				<%
					}
										}
				
				}else{%>
				<%=Dbc.Property.get(temp_p).value%><br>
				<% 
				} 
				}
				}
		    %>
		   </td>
		   <td>
		   		   <%
		   	if (Dbc.Property.get(p).valueType.equals("wikibase-item")|| Dbc.Property.get(p).valueType.equals("wikibase-property"))
		   	{
					Dbc_value.searchInLabels(Dbc.Property.get(p).value);%>
					<%
					for (int k = 0; k < Dbc_value.Labels.size(); k++) {
											if (!Dbc_value.Labels.get(k).language.equals("en")) {
				%> <%=Dbc_value.Labels.get(k).content%><br>
				<%
					}
										}
				
				}else{%>
				<%=Dbc.Property.get(p).value%><br>
				<% 
				}
				
				if(p+1<Dbc.Property.size()){
				int temp_p = p+1;
				while(temp_p<Dbc.Property.size()&&Dbc.Property.get(temp_p).property_id.equals(Dbc.Property.get(temp_p-1).property_id))
				{
				   		   	if (Dbc.Property.get(temp_p).valueType.equals("wikibase-item")|| Dbc.Property.get(temp_p).valueType.equals("wikibase-property"))
		   	{
					Dbc_value.searchInLabels(Dbc.Property.get(temp_p).value);%>
					<%
					for (int k = 0; k < Dbc_value.Labels.size(); k++) {
											if (!Dbc_value.Labels.get(k).language.equals("en")) {
				%> <%=Dbc_value.Labels.get(k).content%><br>
				<%
					}
										}
				
				}else{%>
				<%=Dbc.Property.get(temp_p).value%><br>
				<% 
				} 
				temp_p++;
				}
				p=temp_p;
				}else{
				p=p+1;
				}
		    %>
		   </td>
		   </tr>
		   <% 
		} 
		 %>
	</table>
	</div>
	<br>
	<br>
	<%
		}
		}
		else{
			%>
			<div><br><br><br>Search String Is Not Catch !<br><br><br>未搜索到该关键词！</div>
			<% 
		}
		}
		}else{
			%>
			<div><br><br><br>Search String Is NULL,Please Input A Keyword !<br><br><br>搜索值为空，请重新输入！</div>
			<% 
		}
	%>
</body>
</html>
