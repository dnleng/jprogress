%set (0, 'defaultaxesfontname', 'Helvetica')
%set (0, 'defaultaxesfontsize', 14)
%set (0, 'defaulttextfontname', 'Helvetica')
%set (0, 'defaulttextfontsize', 14) 

set (0, 'defaultaxesfontname', 'Times')
set (0, 'defaultaxesfontsize', 14)
set (0, 'defaulttextfontname', 'Times')
set (0, 'defaulttextfontsize', 14) 


%% Load data for F20
maxLength100 = 0;
for i = 5:190
    disp(int2str(i));
    perfMatrix = csvread(strcat('exp9/kr-online-1-',int2str(i),'-F100.csv'),1,0);
    durationMatrix = perfMatrix(:,20:25);
    durationTotalMs{i} = sum(durationMatrix(:,6))/1000000;
    verdictTrue = perfMatrix(:,3);
    verdictFalse = perfMatrix(:,4);
    verdictUnknown = perfMatrix(:,5);
    verdictNone = perfMatrix(:,6);
    dataFalse{i} = verdictFalse;
    maxLength100 = max(maxLength100, size(verdictFalse));
    dataUnknown{i} = verdictUnknown;
    maxLength100 = max(maxLength100, size(verdictUnknown));
end

M100 = zeros(maxLength100(1),186);
N100 = zeros(maxLength100(1),186);
j = 1;
for i = 5:190
    disp(int2str(i));
    unknownSize = size(dataUnknown{i});
    falseSize = size(dataFalse{i});
    for l = 1:maxLength100(1)
        if l <= unknownSize(1)
            M100(l, j) = dataUnknown{i}(l);
        else
            M100(l, j) = M100(l-1, j);
        end
        if l <= falseSize(1)
            N100(l, j) = dataFalse{i}(l);
        else
            N100(l, j) = N100(l-1, j);
        end
    end
    j = j + 1;
end


%% Load data for F80
maxLength80 = 0;
for i = 5:190
    disp(int2str(i));
    perfMatrix = csvread(strcat('exp9/kr-online-1-',int2str(i),'-F80.csv'),1,0);
    durationMatrix = perfMatrix(:,20:25);
    durationTotalMs{i} = sum(durationMatrix(:,6))/1000000;
    verdictTrue = perfMatrix(:,3);
    verdictFalse = perfMatrix(:,4);
    verdictUnknown = perfMatrix(:,5);
    verdictNone = perfMatrix(:,6);
    dataFalse{i} = verdictFalse;
    maxLength80 = max(maxLength80, size(verdictFalse));
    dataUnknown{i} = verdictUnknown;
    maxLength80 = max(maxLength80, size(verdictUnknown));
end

M80 = zeros(maxLength80(1),186);
N80 = zeros(maxLength80(1),186);
j = 1;
for i = 5:190
    disp(int2str(i));
    unknownSize = size(dataUnknown{i});
    falseSize = size(dataFalse{i});
    for l = 1:maxLength80(1)
        if l <= unknownSize(1)
            M80(l, j) = dataUnknown{i}(l);
        else
            M80(l, j) = M80(l-1, j);
        end
        if l <= falseSize(1)
            N80(l, j) = dataFalse{i}(l);
        else
            N80(l, j) = N80(l-1, j);
        end
    end
    j = j + 1;
end


%% Load data for F60
maxLength60 = 0;
for i = 5:190
    disp(int2str(i));
    perfMatrix = csvread(strcat('exp9/kr-online-1-',int2str(i),'-F60.csv'),1,0);
    durationMatrix = perfMatrix(:,20:25);
    durationTotalMs{i} = sum(durationMatrix(:,6))/1000000;
    verdictTrue = perfMatrix(:,3);
    verdictFalse = perfMatrix(:,4);
    verdictUnknown = perfMatrix(:,5);
    verdictNone = perfMatrix(:,6);
    dataFalse{i} = verdictFalse;
    maxLength60 = max(maxLength60, size(verdictFalse));
    dataUnknown{i} = verdictUnknown;
    maxLength60 = max(maxLength60, size(verdictUnknown));
end

M60 = zeros(maxLength60(1),186);
N60 = zeros(maxLength60(1),186);
j = 1;
for i = 5:190
    disp(int2str(i));
    unknownSize = size(dataUnknown{i});
    falseSize = size(dataFalse{i});
    for l = 1:maxLength60(1)
        if l <= unknownSize(1)
            M60(l, j) = dataUnknown{i}(l);
        else
            M60(l, j) = M60(l-1, j);
        end
        if l <= falseSize(1)
            N60(l, j) = dataFalse{i}(l);
        else
            N60(l, j) = N60(l-1, j);
        end
    end
    j = j + 1;
end


%% Load data for F40
maxLength40 = 0;
for i = 5:190
    disp(int2str(i));
    perfMatrix = csvread(strcat('exp9/kr-online-1-',int2str(i),'-F40.csv'),1,0);
    durationMatrix = perfMatrix(:,20:25);
    durationTotalMs{i} = sum(durationMatrix(:,6))/1000000;
    verdictTrue = perfMatrix(:,3);
    verdictFalse = perfMatrix(:,4);
    verdictUnknown = perfMatrix(:,5);
    verdictNone = perfMatrix(:,6);
    dataFalse{i} = verdictFalse;
    maxLength40 = max(maxLength40, size(verdictFalse));
    dataUnknown{i} = verdictUnknown;
    maxLength40 = max(maxLength40, size(verdictUnknown));
end

M40 = zeros(maxLength40(1),186);
N40 = zeros(maxLength40(1),186);
j = 1;
for i = 5:190
    disp(int2str(i));
    unknownSize = size(dataUnknown{i});
    falseSize = size(dataFalse{i});
    for l = 1:maxLength40(1)
        if l <= unknownSize(1)
            M40(l, j) = dataUnknown{i}(l);
        else
            M40(l, j) = M40(l-1, j);
        end
        if l <= falseSize(1)
            N40(l, j) = dataFalse{i}(l);
        else
            N40(l, j) = N40(l-1, j);
        end
    end
    j = j + 1;
end


%% Plot results
subplot(1,2,1);
index = 5:190;
%qx = [0     190   190   0   ];
%qy = [1.00  1.00  0.99  0.99];
%patch(qx, qy, [1 1 1]*0.8, 'LineStyle', 'None');
%hold on;
plot(index,M40(maxLength40(1),:),'-black');
hold on;
plot(index,M60(maxLength60(1),:),':black');
hold on;
plot(index,M80(maxLength80(1),:),'-.black');
hold on;
plot(index,M100(maxLength100(1),:),'--black');
hold off;
grid on;
axis([5 190 0.0 1.0])
xlabel('MAX\_NODES');
y = ylabel('Leaked Probability at Termination');
set(y, 'Units', 'Normalized', 'Position', [-0.18, 0.5, 0]);
legend('0.4','0.6','0.8','1.0','Location','southoutside', 'Orientation', 'horizontal');
%legend('boxoff');


subplot(1,2,2);
index = 5:190;
%qx = [0     190   190   0   ];
%qy = [1.00  1.00  0.99  0.99];
%patch(qx, qy, [1 1 1]*0.8, 'LineStyle', 'None');
%hold on;
plot(index,N40(maxLength40(1),:),'-black');
hold on;
plot(index,N60(maxLength60(1),:),':black');
hold on;
plot(index,N80(maxLength80(1),:),'-.black');
hold on;
plot(index,N100(maxLength100(1),:),'--black');
hold off;
grid on;
axis([5 190 0.0 1.0])
xlabel('MAX\_NODES');
y = ylabel('False Probability at Termination');
set(y, 'Units', 'Normalized', 'Position', [-0.18, 0.5, 0]);
legend('0.4','0.6','0.8','1.0','Location','southoutside', 'Orientation', 'horizontal');
%legend('boxoff');


%% Print figures
print('graph', '-dpng', '-r150');
