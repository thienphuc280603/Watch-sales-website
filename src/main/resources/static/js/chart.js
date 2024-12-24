google.charts.load('current', { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawChart);

var thang11 = document.getElementById("thang11");

function drawChart() {
  // Convert thang1Chart.value to a number
  const thang11 = parseFloat(thang1Chart.value);
 alert(thang11);
  // Set Data
  const data = google.visualization.arrayToDataTable([
	
    ['Month', 'Total Price'],
    [1, thang11], [2, 200000000], [3, 300000000], [4, 400000000], [5, 150000000],
    // Add more months as needed
  ]);

  // Set Options
  const options = {
    title: 'House Prices vs. Size',
    hAxis: { title: 'Month' },
    vAxis: { title: 'Total Price' },
    legend: 'none'
  };

  // Draw
  const chart = new google.visualization.LineChart(document.getElementById('myChart'));
  chart.draw(data, options);
}
