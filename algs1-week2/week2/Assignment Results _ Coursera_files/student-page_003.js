define(["jquery","backbone","underscore","pages/spark/views/template/student-page.html","js/lib/api","js/core/coursera","js/lib/cookie","bundles/assess/assessmentTypes/sparkSurveyQuestions/sparkSurveyQuestionsSessionModel"],function($,Backbone,_,template,API,Coursera,cookie,SparkSurveyQuestionsSessionModel){var page=Backbone.View.extend({name:"page",subregions:{header:"pages/spark/views/template/header",sidebar:"pages/spark/views/template/sidebar"},initialize:function(){this.bind("view:appended",this.scroll),this.bind("view:updated",this.scroll)},scroll:function(){$("html, body").scrollTop(0),$(window).trigger("scroll")},renderPrioritySupportButton:function(){function showSupportButton(network_name){$("[data-networkprioritysupportwidget]").text(network_name+" Priority Support"),$("[data-networkprioritysupportwidget]").show()}var cookieName="network-priority-support",cookieValue=cookie.get(cookieName);if(cookieValue){try{cookieValue=JSON.parse(cookieValue)}catch(e){return Coursera.multitracker.push(["CPP Priority Cookie Value",cookieValue]),void cookie.clear(cookieName)}if(cookieValue.network_name)showSupportButton(cookieValue.network_name);return}if(Math.random()>.25)return;var path="signature/user/get_premium_support_network",postData={data:{"user-id":Coursera.user.get("id"),"session-id":Coursera.course.get("id")},success:function(d){if(null!==d&&d.network_name)cookie.set(cookieName,JSON.stringify({network_name:d.network_name}),{expires:7,path:"/"+Coursera.course.get("sessionName")}),showSupportButton(d.network_name);else if(null!==d&&d.error)cookie.set(cookieName,JSON.stringify({error:d.error}),{expires:7,path:"/"+Coursera.course.get("sessionName")})},error:function(){}};Coursera.api.get(path,postData)},render:function(){var regions=this.region.regions,self=this,page=$(template()),model=new SparkSurveyQuestionsSessionModel({classId:Coursera.course.id});return self.$el.append(page),_.each(["header","sidebar","body"],function(type){if(!regions[type]){if("body"!=type)page.find(".coursera-"+type).hide();else page.find(".coursera-body").replaceWith($("#spark").show());return}regions[type].view.model=model,page.find(".coursera-"+type).replaceWith(regions[type].view.el)}),this.renderPrioritySupportButton(),self}});return page});