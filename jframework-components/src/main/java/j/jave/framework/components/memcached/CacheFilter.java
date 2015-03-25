/**
 * 
 */
package j.jave.framework.components.memcached;


/**
 * @author Administrator
 *
 */
public class CacheFilter {

	
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        try {
//            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
//            HttpServletResponse httpServletResponse = (HttpServletResponse)response;
//            ObjectPool<MemcachedClient> MemcachedClientPool = MemcachedHelper.getMemcachedConnectionPool();
//            MemcachedClient  MemcachedClient = MemcachedClientPool.borrowObject();
//            StringBuffer cacheKeyBuffer = new StringBuffer();
//            cacheKeyBuffer.append(httpServletRequest.getContextPath());
//            cacheKeyBuffer.append(httpServletRequest.getServletPath());
//            if(httpServletRequest.getQueryString() != null){
//                cacheKeyBuffer.append("?");
//                cacheKeyBuffer.append(httpServletRequest.getQueryString());
//            }
//            
//            String cacheKey = httpServletResponse.encodeURL(cacheKeyBuffer.toString());
//            System.out.println ("Get Path Info  " + cacheKey);
//            String cachedResponse =(String) MemcachedClient.get(cacheKey);
//            
//            if( cachedResponse == null){
//                System.out.println("Response is not cached forwarding control to servlet");
//                CachingResponseWrapper cachingResponseWrapper =new CachingResponseWrapper((HttpServletResponse)response);
//                chain.doFilter(request, cachingResponseWrapper);
//                CachingResponseWriter collectResponseWriter = (CachingResponseWriter)cachingResponseWrapper.getWriter();
//                String collectedResponseStr = collectResponseWriter.getCollectedResponse();//.replaceAll("\n", "") ;
//                System.out.println( "Set value in the Memcached for key " + httpServletResponse.encodeURL(collectedResponseStr));
//                
//                System.out.println("Result of set" + MemcachedClient.set(cacheKey, 0, collectedResponseStr).get());
//                //MemcachedClient.flush().get();
//            }else{
//                System.out.println("Returning cached response ");
//                response.setContentType("text/html");
//                response.getWriter().println(cachedResponse);
//            }
//            //MemcachedClient.flush().get();
//            MemcachedClientPool.returnObject(MemcachedClient);
//        } catch (NoSuchElementException e) {
//            e.printStackTrace();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
	
}
